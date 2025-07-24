package com.jobconnect.common.storage;

import com.jobconnect.common.constants.DevConstants;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FilePartStorage {

    public Mono<String> storeFile(FilePart filePart, String userName) throws IOException {

        Path storagePath = Paths.get(DevConstants.DEFAULT_UPLOAD_DIR);
        if (!Files.exists(storagePath)) {
            Files.createDirectories(storagePath);
        }

        String filename = filePart.filename();
        String storeFileName = userName + "_resume_" + System.currentTimeMillis() + filename.substring(filename.lastIndexOf('.'));
        Path destination = storagePath.resolve(storeFileName);

        return filePart.transferTo(destination).thenReturn("File stored successfully: " + storeFileName);

    }

}
