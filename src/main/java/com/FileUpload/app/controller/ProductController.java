package com.FileUpload.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.FileUpload.app.awsConfig.UploadFileToAwsHandler;
import com.FileUpload.app.model.Product;
import com.FileUpload.app.service.ProductService;
import com.FileUpload.app.utils.Response;

@RestController
@RequestMapping("v1/imageProduct")
@CrossOrigin(origins = "https://awss3imageupload.netlify.app/")
public class ProductController {
	@GetMapping("health")
	public String getHealth() {
		return "aws application running good";
	}
	ResponseEntity responseObject = null;
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UploadFileToAwsHandler uploadFileToAwsHandler;
	
	@PostMapping("add-products")
	public ResponseEntity<?> addProductWithImage(
	    @RequestParam("file") MultipartFile file,
	    @ModelAttribute Product product
	) {
	    try {
	        uploadFileToAwsHandler.handlePdfUploadAndAddProduct(file, product);
	        productService.addProduct(product); // Save product to database
	        return new ResponseEntity<>("Product added successfully with image!", HttpStatus.CREATED);
	    } catch (Exception e) {
	        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
	 @GetMapping("list-Products")
	 public ResponseEntity<List<Product>> ListProducts(){
		 List<Product> products = productService.list();
		 return new ResponseEntity<>(products, HttpStatus.OK);
	 }
	 @GetMapping("get-Product/{id}")
	 public ResponseEntity getProduct(@PathVariable Long id ) {
		 try {
			 Product product = productService.getProductById(id);
			 responseObject = new ResponseEntity(product, HttpStatus.OK);
		 }catch(Exception exception) {
			 Response response = new Response();
	            response.setMessage(exception.getMessage());
	            response.setOperation(false);
	            response.setStatusCode(500);
	            System.out.println(exception);
	            responseObject = new ResponseEntity<>(response, HttpStatus.OK);
		 }
		 return responseObject;
	 }
}