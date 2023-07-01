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

    /**
     * function: danh sách và tìm kiếm các trường ở trong cửa hằng
     *
     * @param page
     * @param size
     * @param productName
     * @param categoryId
     * @return
     */
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

    /**
     * function: danh sách cho trang chủ
     *
     * @return
     */
    @GetMapping("/page-list")
    public ResponseEntity<?> getListProductIsFalse() {
        List<Product> list = productService.findAllByIsDeleteIsFalse();
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
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

    @PatchMapping("/update/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable("productId") Long productId, @Validated @RequestBody ProductDTO productDTO,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // Kiểm tra sự tồn tại của sản phẩm
        Product existingProduct = productService.findById(productId);
        if (existingProduct == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Cập nhật thông tin sản phẩm từ DTO
        existingProduct.setProductName(productDTO.getProductName());
        existingProduct.setProductDescription(productDTO.getProductDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setCost(productDTO.getCost());
        existingProduct.setQuantity(productDTO.getQuantity());

        productService.saveProduct(existingProduct);

        return new ResponseEntity<>(existingProduct, HttpStatus.OK);
    }


    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDetailProduct(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        product.setQuantity(1);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/update-detail/{id}")
    public ResponseEntity<?> getDetailForUpdate(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        productService.softDeleteById(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/SON")
    public ResponseEntity<?> getListSON() {
        List<Product> productList = productService.getListSON();
        return new ResponseEntity<>(productList, HttpStatus.OK);

    }

    @GetMapping("/KEM-NEN")
    public ResponseEntity<?> getListKEMNEN() {
        List<Product> productList = productService.getListKEMNEN();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/TAY-TRANG")
    public ResponseEntity<?> getListTAYTRANG() {
        List<Product> productList = productService.getListTAYTRANG();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/XIT-KHOANG")
    public ResponseEntity<?> getListXITKHOANG() {
        List<Product> productList = productService.getListXITKHOANG();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/BANG-MAT")
    public ResponseEntity<?> getListBANGMAT() {
        List<Product> productList = productService.getListBANGMAT();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/PHAN-MA")
    public ResponseEntity<?> getListPHANMA() {
        List<Product> productList = productService.getListPHANMA();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/CKD")
    public ResponseEntity<?> getListCHEKHUYETDIEM() {
        List<Product> productList = productService.getListCHEKHUYETDIEM();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/PHAN-PHU")
    public ResponseEntity<?> getListPHANPHU() {
        List<Product> productList = productService.getListPHANPHU();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/HIGHLIGHT")
    public ResponseEntity<?> getListHIGHLIGHT() {
        List<Product> productList = productService.getListHIGHLIGHT();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/TRIET-SAC")
    public ResponseEntity<?> getListTRIETSAC() {
        List<Product> productList = productService.getListTRIETSAC();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/filter-prices")
    public ResponseEntity<?> filterPrices(@RequestParam(required = false, defaultValue = "0") int page,
                                          @RequestParam(required = false, defaultValue = "5") int size,
                                          @RequestParam double minPrice,
                                          @RequestParam double maxPrice) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productList = productService.filterPrices(minPrice, maxPrice, pageable);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

}
