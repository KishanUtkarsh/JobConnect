package com.jobconnect.common.storage;


import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jobconnect.common.constants.DevConstants;
import com.jobconnect.common.exception.FileNotFoundException;
import com.jobconnect.common.exception.FileUploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public Mono<String> storeFile(FilePart file, String userName) throws FileUploadException {

        String filename = file.filename();
        String storeFileName = userName + "_resume_" + System.currentTimeMillis() + filename.substring(filename.lastIndexOf('.'));

        // Store the file in local first because we can't directly upload FilePart to S3
        // FilePart is not directly compatible with S3's PutObjectRequest
        Path tempFile = Paths.get(
                System.getProperty("java.io.tmpdir"),
                storeFileName
        );

        return file.transferTo(tempFile)
                .then(Mono.fromCallable(() -> {
                    File newFile = tempFile.toFile();
                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentLength(newFile.length());
                    metadata.setContentType(Files.probeContentType(tempFile));
                    amazonS3.putObject(
                            new PutObjectRequest(
                                    bucketName,
                                    storeFileName,
                                    new FileInputStream(newFile),
                                    metadata
                            )
                    );
                    file.delete(); // optionally delete temporary file after upload
                    return storeFileName;
                }));

    }

    @Override
    public String retrieveFile(String filename) {
        return this.getFileUrl(filename);
    }

    @Override
    public void deleteFile(String filename) {
        try {
            amazonS3.deleteObject(bucketName, filename);
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
