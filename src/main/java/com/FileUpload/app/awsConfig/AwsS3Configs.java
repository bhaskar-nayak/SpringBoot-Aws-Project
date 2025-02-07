package com.FileUpload.app.awsConfig;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class AwsS3Configs {
	@Value("${AWS_ACCESS_KEY}")
	private String accessKey;
	@Value("${AWS_SECRET_KEY}")
	private String secretKey;
	
	private S3Client s3Client;

    @PostConstruct
    public void init() {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        this.s3Client = S3Client.builder()
                .region(Region.of("eu-north-1"))
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .build();
    }

    public String uploadToAwsFile(MultipartFile inputFile, String fileName) {
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket("youtube-first-png-file-upload")
                    .key(fileName)
                    .contentType("image/jpeg")
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(inputFile.getInputStream(), inputFile.getSize()));

            String fileUrl = "https://youtube-first-png-file-upload.s3.eu-north-1.amazonaws.com/" + fileName;

            System.out.println("File uploaded successfully to S3. URL: " + fileUrl);
            return fileUrl;
        } catch (IOException e) {
            System.err.println("Error uploading file: " + e.getMessage());
            return null;
        }
    }
}
