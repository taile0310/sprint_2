package com.example.sprint2.service;

import com.example.sprint2.model.OrderDetail;
import com.example.sprint2.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface IProductService {
    Page<Product> listProduct(Pageable pageable);
    void saveProduct(Product product);
    Product findById( Long id);

    void deleteById(@Param("id") Long id);

    Page<Product> searchTwoField(String productName, Long categoryId, Pageable pageable);
    Page<Product> searchOneField(String productName, Pageable pageable);
    List<Product> findAllByIsDeleteIsFalse();

    Product getProductId( Long productId);

    boolean softDeleteById(Long productId);
    List<Product> getListSON();
    List<Product> getListKEMNEN();
    List<Product> getListTAYTRANG();
    List<Product> getListXITKHOANG();
    List<Product> getListBANGMAT();
    List<Product> getListPHANMA();
    List<Product> getListCHEKHUYETDIEM();
    List<Product> getListPHANPHU();
    List<Product> getListHIGHLIGHT();
    List<Product> getListTRIETSAC();
    Page<Product> filterPrices(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice, Pageable pageable);

}
