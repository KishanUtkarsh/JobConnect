package com.jobconnect.common.storage;

import com.jobconnect.common.constants.DevConstants;
import com.jobconnect.common.exception.FileNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Profile("dev")
@Service
public class LocalFileStorageService implements FileStorageService{



    @Override
    public String storeFile(MultipartFile file, String userName) throws IOException {

        Path storagePath = Paths.get(DevConstants.DEFAULT_UPLOAD_DIR);
        if (!Files.exists(storagePath)) {
            Files.createDirectories(storagePath);
        }

        String filename = file.getOriginalFilename();
        String storeFileName = userName + "_resume_" + System.currentTimeMillis() + filename.substring(filename.lastIndexOf('.'));

        Path destination = storagePath.resolve(storeFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return storeFileName;
    }

    @Override
    public String retrieveFile(String filename) {
        return "";
    }

    @Override
    public String deleteFile(String filename) throws IOException {
        Path filePath = Paths.get(DevConstants.DEFAULT_UPLOAD_DIR, filename);
        if (!Files.exists(filePath)) {
            return new FileNotFoundException("File not found: " + filename).getMessage();
        }
        Files.delete(filePath);
        return "Deleted file: " + filename;
    }

    @Override
    public String getFileUrl(String filename) {
        return DevConstants.DEFAULT_UPLOAD_DIR + "/" + filename;
    }
}
