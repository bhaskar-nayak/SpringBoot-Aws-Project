package com.FileUpload.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FileUpload.app.model.Product;
import com.FileUpload.app.repo.ProductRepository;
import com.FileUpload.app.utils.Response;
@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	@Override
	public Response addProduct(Product product) {
		Response response = new Response();
		try {
			productRepository.save(product);
			response.setMessage("Adding product Successful");
			response.setOperation(true);
			response.setStatusCode(200);
		}catch(Exception e) {
			response.setMessage("Adding product Failed");
			response.setOperation(false);
			response.setStatusCode(500);
			e.printStackTrace();
		}
		return response;
	}
	@Override
	public List<Product> list() {
		
		return(List<Product>) productRepository.findAll();
	}
	@Override
	public Product getProductById(Long id) {
		
		return productRepository.findById(id).get();
	}
}
