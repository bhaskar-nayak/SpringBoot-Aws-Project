package com.FileUpload.app.repo;

import org.springframework.data.repository.CrudRepository;

import com.FileUpload.app.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

}
