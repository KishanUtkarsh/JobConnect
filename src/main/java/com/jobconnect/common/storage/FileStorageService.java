package com.jobconnect.common.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    String storeFile(MultipartFile file, String userName) throws IOException;
    String retrieveFile(String filename);
    String deleteFile(String filename) throws IOException;
    String getFileUrl(String filename);
}
