package com.example.sprint2.repository;

import com.example.sprint2.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByProductNameContainingIgnoreCaseAndCategory_Id(String productName, Long categoryId, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCase(String productName, Pageable pageable);

    @Query(value = "select products.price from products where id = :productId", nativeQuery = true)
    Product getProductId(@Param("productId") Long productId);

}
