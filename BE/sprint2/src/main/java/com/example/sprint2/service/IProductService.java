package com.example.sprint2.service;

import com.example.sprint2.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface IProductService {
    Page<Product> listProduct(Pageable pageable);
    void saveProduct(Product product);
    Product findById( Long id);

    void deleteById(@Param("id") Long id);



}
