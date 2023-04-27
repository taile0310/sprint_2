package com.example.sprint2.service;

import com.example.sprint2.model.Category;
import com.example.sprint2.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IProductService {
    Page<Product> listProduct(Pageable pageable);
    void saveProduct(Product product);
    Product findById( Long id);

    void deleteById(@Param("id") Long id);

    Page<Product> searchTwoField(String productName, Long categoryId, Pageable pageable);
    Page<Product> searchOneField(String productName, Pageable pageable);


}
