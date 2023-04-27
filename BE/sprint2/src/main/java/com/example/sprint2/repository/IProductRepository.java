package com.example.sprint2.repository;

import com.example.sprint2.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByProductNameContainingIgnoreCaseAndCategory_Id(String productName, Long categoryId, Pageable pageable);
    Page<Product> findByProductNameContainingIgnoreCase(String productName, Pageable pageable);

}
