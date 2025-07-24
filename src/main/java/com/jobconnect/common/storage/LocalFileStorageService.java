package com.jobconnect.common.storage;

import com.jobconnect.common.constants.DevConstants;
import com.jobconnect.common.exception.FileNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Profile("dev")
@Service
public class LocalFileStorageService implements FileStorageService{



    @Override
    public Mono<String> storeFile(FilePart file, String userName) throws IOException {

        Path storagePath = Paths.get(DevConstants.DEFAULT_UPLOAD_DIR);
        if (!Files.exists(storagePath)) {
            Files.createDirectories(storagePath);
        }

        String filename = file.filename();
        String storeFileName = userName + "_resume_" + System.currentTimeMillis() + filename.substring(filename.lastIndexOf('.'));

        Path destination = storagePath.resolve(storeFileName);
        return file.transferTo(destination).thenReturn(storeFileName);
    }

    @Override
    public String retrieveFile(String filename) {
        return this.getFileUrl(filename);
    }

    @Override
    public void deleteFile(String filename) throws IOException {
        Path filePath = Paths.get(DevConstants.DEFAULT_UPLOAD_DIR, filename);
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found: " + filename);
        }
        Files.delete(filePath);
    }

    @Override
    public String getFileUrl(String filename) {
        return DevConstants.DEFAULT_UPLOAD_DIR + "/" + filename;
    }
}
