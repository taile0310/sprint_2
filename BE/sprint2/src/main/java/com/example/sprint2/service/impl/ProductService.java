package com.example.sprint2.service.impl;


import com.example.sprint2.model.Product;
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
        return productRepository.findByProductNameContainingIgnoreCaseAndCategory_IdAndIsDeleteIsFalse(productName, categoryId, pageable);
    }

    @Override
    public Page<Product> searchOneField(String productName, Pageable pageable) {
        return productRepository.findByProductNameContainingIgnoreCaseAndIsDeleteIsFalse(productName,pageable);
    }

    @Override
    public List<Product> findAllByIsDeleteIsFalse() {
        return productRepository.findAllByIsDeleteIsFalse();
    }

    @Override
    public Product getProductId(Long productId) {
        return productRepository.getProductId(productId);
    }


    @Override
    public boolean softDeleteById(Long productId) {
        if (productRepository.findById(productId) != null) {
            productRepository.softDeleteById(productId);
            return true;
        }
        return false;
    }

    @Override
    public List<Product> getListSON() {
        return productRepository.getListSON();
    }

    @Override
    public List<Product> getListKEMNEN() {
        return productRepository.getListKEMNEN();
    }

    @Override
    public List<Product> getListTAYTRANG() {
        return productRepository.getListTAYTRANG();
    }

    @Override
    public List<Product> getListXITKHOANG() {
        return productRepository.getListXITKHOANG();
    }

    @Override
    public List<Product> getListBANGMAT() {
        return productRepository.getListBANGMAT();
    }

    @Override
    public List<Product> getListPHANMA() {
        return productRepository.getListPHANMA();
    }

    @Override
    public List<Product> getListCHEKHUYETDIEM() {
        return productRepository.getListCHEKHUYETDIEM();
    }

    @Override
    public List<Product> getListPHANPHU() {
        return productRepository.getListPHANPHU();
    }

    @Override
    public List<Product> getListHIGHLIGHT() {
        return productRepository.getListHIGHLIGHT();
    }

    @Override
    public List<Product> getListTRIETSAC() {
        return productRepository.getListTRIETSAC();
    }

    @Override
    public Page<Product> filterPrices(double minPrice, double maxPrice, Pageable pageable) {
        return productRepository.filterPrices(minPrice,maxPrice, pageable);
    }


}
