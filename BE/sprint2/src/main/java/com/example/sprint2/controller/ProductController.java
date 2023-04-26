package com.example.sprint2.controller;

import com.example.sprint2.dto.ProductDTO;
import com.example.sprint2.model.Product;
import com.example.sprint2.model.User;
import com.example.sprint2.service.IProductService;
import com.example.sprint2.service.impl.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("api/public/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping("/list")
    public ResponseEntity<?> getListProduct(@RequestParam(required = false, defaultValue = "0") int page,
                                            @RequestParam(required = false, defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.listProduct(pageable);
        if (productPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(productPage, HttpStatus.OK);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewProduct(@Validated @RequestBody ProductDTO productDTO,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        productService.saveProduct(product);
        return new ResponseEntity<>(product, HttpStatus.OK);

    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @Validated @RequestBody ProductDTO productDTO,
                                           BindingResult bindingResult) {
        Product product = productService.findById(id);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(productDTO, product);
        productService.saveProduct(product);
        return new ResponseEntity<>(product, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDetailProduct(@PathVariable("id") Long id){
        Product product = productService.findById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
