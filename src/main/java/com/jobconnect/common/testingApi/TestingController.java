package com.jobconnect.common.testingApi;

import com.jobconnect.common.storage.FilePartStorage;
import com.jobconnect.common.storage.S3FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController(value = "testingController")
@RequestMapping("/api/public/testing")
@Tag(name = "Testing Controller", description = "Testing related operations")
public class TestingController {

    FilePartStorage filePartStorage;

    S3FileStorageService s3FileStorageService;

    public TestingController(FilePartStorage filePartStorage, S3FileStorageService s3FileStorageService) {
        this.filePartStorage = filePartStorage;
        this.s3FileStorageService = s3FileStorageService;
    }

    @GetMapping("/upload")
    public Mono<String> filePartTest(@RequestPart("filePart") FilePart filePart) throws IOException {
        String userName = "testUser"; // Replace with actual username logic if needed
        return filePartStorage.storeFile(filePart, userName);
    }

    @GetMapping("/s3-upload")
    public Mono<String> s3FilePartTest(@RequestPart("filePart") FilePart filePart) throws IOException {
        String userName = "testUser"; // Replace with actual username logic if needed
        return s3FileStorageService.storeFile(filePart, userName);
    }

}
