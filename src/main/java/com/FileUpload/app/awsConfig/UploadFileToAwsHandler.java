package com.FileUpload.app.awsConfig;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.FileUpload.app.model.Product;

@Service
public class UploadFileToAwsHandler {
	@Autowired
	private AwsS3Configs awsS3Configs;
	public void handlePdfUploadAndAddProduct(MultipartFile inputFile, Product product) throws Exception {
	    System.out.println("inside service");

	    String fileName = StringUtils.cleanPath(inputFile.getOriginalFilename());
	    String fileExtensions = StringUtils.getFilenameExtension(fileName);
	    if (!"jpg".equalsIgnoreCase(fileExtensions)) {
	        throw new Exception(fileExtensions + " File is not allowed. Please upload JPGs only.");
	    }

	    long fileSizeLimit = 1024 * 1024;
	    if (inputFile.getSize() > fileSizeLimit) {
	        throw new Exception("File size exceeds the limit of 1MB.");
	    }

	    String outputFileName = UUID.randomUUID().toString() + ".jpg";
	    String imageUrl = awsS3Configs.uploadToAwsFile(inputFile, outputFileName);

	    // Associate the image URL with the product
	    product.setImageUrl(imageUrl);
	}
}
