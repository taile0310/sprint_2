package com.example.sprint2.service.impl;

import com.example.sprint2.model.Category;
import com.example.sprint2.model.Product;
import com.example.sprint2.repository.ICategoryRepository;
import com.example.sprint2.repository.IProductRepository;
import com.example.sprint2.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Override
    public Page<Product> listProduct(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> searchTwoField(String productName, Long categoryId, Pageable pageable) {
        return productRepository.findByProductNameContainingIgnoreCaseAndCategory_Id(productName, categoryId, pageable);
    }

    @Override
    public Page<Product> searchOneField(String productName, Pageable pageable) {
        return productRepository.findByProductNameContainingIgnoreCase(productName, pageable);
    }


}
