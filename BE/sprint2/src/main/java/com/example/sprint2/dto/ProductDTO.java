package com.example.sprint2.dto;

import com.example.sprint2.model.Category;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class ProductDTO implements Validator {
    private Long id;
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Pattern(regexp = "^[a-z0-9 _-]{3,50}$", message = "Tên sản phẩm không đúng định dạng")
    private String productName;
    @NotBlank(message = "Mô tả sản phẩm không được để trống")

    private String productDescription;

    @NotBlank(message = "Giá bán sản phẩm không được để trống")

    private Double price;
    @NotBlank(message = "Giá gốc sản phẩm không được để trống")

    private Double cost;
    @NotBlank(message = "Hình ảnh sản phẩm không được để trống")

    private String image;

    @NotBlank(message = "Số lượng sản phẩm không được để trống")
    private Integer quantity;
    @NotBlank(message = "Loại sản phẩm không được để trống")

    private Category category;

    private boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductDTO productDTO = (ProductDTO) target;
        if (productDTO.price < 0) {
            errors.rejectValue("price", "Giá bán không được âm", "Giá bán không được âm");
        }
        if (productDTO.cost < 0) {
            errors.rejectValue("price", "Giá gốc không được âm", "Giá gốc không được âm");
        }

        if (productDTO.quantity < 0) {
            errors.rejectValue("quantity", "Số lượng không được âm", "Số lượng không được âm");
        }
    }
}
