package com.jobconnect.common.storage;


import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jobconnect.common.exception.FileNotFoundException;
import com.jobconnect.common.exception.FileUploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Date;

@Slf4j
@Profile("prod")
@Service
public class S3FileStorageService implements FileStorageService {

    /**
     * This service is responsible for storing files in Amazon S3.
     * It will only be active in the 'prod' profile.
     */

    @Value("${app.s3.bucket}")
    private String bucketName;

    private final AmazonS3 amazonS3;
    public S3FileStorageService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public String storeFile(MultipartFile file, String userName) {

        String filename = file.getOriginalFilename();
        String storeFileName = userName + "_resume_" + System.currentTimeMillis() + filename.substring(filename.lastIndexOf('.'));

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try {
            amazonS3.putObject(new PutObjectRequest(
                    bucketName,
                    storeFileName,
                    file.getInputStream(),
                    metadata
            ));
            return storeFileName;
        } catch (Exception e) {
            // Handle exceptions such as IOException or AmazonS3Exception
            throw new FileUploadException("Failed to store file in S3: " + e.getMessage());
        }
    }

    @Override
    public String retrieveFile(String filename) {
        return this.getFileUrl(filename);
    }

    @Override
    public String deleteFile(String filename) {
        try {
            amazonS3.deleteObject(bucketName, filename);
            return "Deleted file: " + filename;
        } catch (Exception e) {
            // Handle exceptions such as AmazonS3Exception
            log.error("Failed to delete file: {}", filename, e);
            throw new FileNotFoundException("File not found or could not be deleted: " + filename + ". " + e.getMessage());
        }
    }

    @Override
    public String getFileUrl(String filename) {

        Date expiration = new Date();
        long time = expiration.getTime();
        time = time + 2 * 1000 * 60 * 60; // 2 hours
        expiration.setTime(time);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
                bucketName,
                filename
        ).withMethod(HttpMethod.GET)
                .withExpiration(expiration);

        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        if (url == null) {
            throw new FileNotFoundException("File not found: " + filename);
        }
        return url.toString();
    }
}
