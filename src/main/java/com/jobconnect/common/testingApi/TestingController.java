package com.jobconnect.common.testingApi;

import com.jobconnect.common.storage.FilePartStorage;
import com.jobconnect.common.storage.LocalFileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController(value = "testingController")
@RequestMapping("/api/public/testing")
@Tag(name = "Testing Controller", description = "Testing related operations")
public class TestingController {

    @Autowired
    private FilePartStorage filePartStorage;

    @Autowired
    private LocalFileStorageService localFileStorageService;


    // This controller is used for testing purposes only.
    // It can be used to test various functionalities without affecting the main application logic.
    // Add your testing methods here as needed.

    @GetMapping("/upload")
    public Mono<String> filePartTest(@RequestPart("filePart") FilePart filePart) throws IOException {
        String userName = "testUser"; // Replace with actual username logic if needed
        return filePartStorage.storeFile(filePart, userName);
    }

}
