package com.example.sprint2.controller;

import com.example.sprint2.dto.ProductDTO;
import com.example.sprint2.model.Category;
import com.example.sprint2.model.Product;
import com.example.sprint2.service.ICategoryService;
import com.example.sprint2.service.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/public/product")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/list-category")
    public ResponseEntity<?> getListCategory() {
        List<Category> categoryList = categoryService.listCategory();
        if (categoryList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }


    @GetMapping("/list")
    public ResponseEntity<?> getListProduct(@RequestParam(required = false, defaultValue = "0") int page,
                                            @RequestParam(required = false, defaultValue = "5") int size,
                                            @RequestParam(required = false, defaultValue = "") String productName,
                                            @RequestParam(required = false, defaultValue = "") Long categoryId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage;
        if (categoryId == 0) {
            productPage = productService.searchOneField(productName, pageable);
        } else {
            productPage = productService.searchTwoField(productName, categoryId, pageable);
        }
        if (productPage.isEmpty()) {
            return new ResponseEntity<>("không tìm thấy sản phẩm", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productPage, HttpStatus.OK);
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
    public ResponseEntity<?> getDetailProduct(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        product.setQuantity(1);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
