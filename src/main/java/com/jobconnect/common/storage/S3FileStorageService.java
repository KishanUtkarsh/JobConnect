package com.jobconnect.common.storage;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Profile("prod")
@Service
public class S3FileStorageService implements FileStorageService {
    @Override
    public String storeFile(MultipartFile file, String userName) {
        // Logic to store file in S3
        return "s3://bucket-name/" + file.getOriginalFilename();
    }

    @Override
    public String retrieveFile(String filename) {
        // Logic to retrieve file from S3
        return "s3://bucket-name/" + filename;
    }

    @Override
    public String deleteFile(String filename) {
        // Logic to delete file from S3
        return "Deleted s3://bucket-name/" + filename;
    }

    @Override
    public String getFileUrl(String filename) {
        return "";
    }
}
