package com.jobconnect.common.storage;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface FileStorageService {
    Mono<String> storeFile(FilePart file, String userName) throws IOException;
    String retrieveFile(String filename);
    void deleteFile(String filename) throws IOException;
    String getFileUrl(String filename);
}
