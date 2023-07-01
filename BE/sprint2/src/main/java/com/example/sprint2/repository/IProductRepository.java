package com.example.sprint2.repository;

import com.example.sprint2.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import javax.transaction.Transactional;
import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByProductNameContainingIgnoreCaseAndCategory_IdAndIsDeleteIsFalse(String productName, Long categoryId, Pageable pageable);

    Page<Product> findByProductNameContainingIgnoreCaseAndIsDeleteIsFalse(String productName, Pageable pageable);

    List<Product> findAllByIsDeleteIsFalse();

    @Query(value = "select products.price from products where id = :productId", nativeQuery = true)
    Product getProductId(@Param("productId") Long productId);

    //xóa mềm
    @Modifying
    @Transactional
    @Query(value = "update products p SET p.is_delete = true where p.id = :productId", nativeQuery = true)
    void softDeleteById(@Param("productId") Long productId);

    @Query(value = "select * from products where category_id = 1", nativeQuery = true)
    List<Product> getListSON();

    @Query(value = "select * from products where category_id = 2", nativeQuery = true)
    List<Product> getListKEMNEN();

    @Query(value = "select * from products where category_id = 3", nativeQuery = true)
    List<Product> getListTAYTRANG();

    @Query(value = "select * from products where category_id = 4", nativeQuery = true)
    List<Product> getListXITKHOANG();

    @Query(value = "select * from products where category_id = 5", nativeQuery = true)
    List<Product> getListBANGMAT();

    @Query(value = "select * from products where category_id = 6", nativeQuery = true)
    List<Product> getListPHANMA();

    @Query(value = "select * from products where category_id = 7", nativeQuery = true)
    List<Product> getListCHEKHUYETDIEM();

    @Query(value = "select * from products where category_id = 8", nativeQuery = true)
    List<Product> getListPHANPHU();

    @Query(value = "select * from products where category_id = 9", nativeQuery = true)
    List<Product> getListHIGHLIGHT();

    @Query(value = "select * from products where category_id = 10", nativeQuery = true)
    List<Product> getListTRIETSAC();

    @Query(value = "SELECT * FROM products WHERE price BETWEEN :minPrice AND :maxPrice", nativeQuery = true)
    Page<Product> filterPrices(@Param("minPrice") double minPrice,
                               @Param("maxPrice") double maxPrice,
                               Pageable pageable);

}
