package com.jobconnect.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

@Slf4j
public class FileConverter {

    private FileConverter(){
        // Private constructor to prevent instantiation
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static MultipartFile convertToMultipartFile(FilePart filePart){
        return new MultipartFile() {
            @Override
            public String getName() {
                return filePart.name();
            }

            @Override
            public String getOriginalFilename() {
                return filePart.filename();
            }

            @Override
            public String getContentType() {
                return filePart.headers().getContentType().toString();
            }

            @Override
            public boolean isEmpty() {
                return filePart.headers().isEmpty();
            }

            @Override
            public long getSize() {
                return filePart.headers().getContentLength();
            }

            @Override
            public byte[] getBytes() throws IOException {
                return filePart.content()
                        .map(dataBuffer -> {
                            byte[] bytes = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(bytes);
                            DataBufferUtils.release(dataBuffer);
                            return bytes;
                        })
                        .collectList()
                        .map(list -> {
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            list.forEach(bytes -> {
                                try {
                                    out.write(bytes);
                                } catch (IOException e) {
                                    throw new UncheckedIOException(e);
                                }
                            });
                            return out.toByteArray();
                        })
                        .block();
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(getBytes());
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                try (InputStream in = getInputStream()) {
                    Files.copy(in, dest.toPath());
                }
            }
        };
    }
}
