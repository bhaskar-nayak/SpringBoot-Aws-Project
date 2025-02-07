package com.FileUpload.app.service;

import java.util.List;

import com.FileUpload.app.model.Product;
import com.FileUpload.app.utils.Response;

public interface ProductService {
	public Response addProduct(Product product);
	public List<Product> list();
	public Product getProductById(Long id);

}
