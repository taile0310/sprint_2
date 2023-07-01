package com.example.sprint2.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDTO {
    private Long id;
    @Size(max = 50, message = "Tên đăng nhập không được quá 50 ký tự.")
    @NotBlank(message = "tên nhân viên không được để trống")
    @Pattern(regexp = "^[a-z0-9]*$", message = "Tên đăng nhập được chứa số, không chứa kí tự đặc biệt")
    private String username;
    @Size(min = 6, max = 20, message = "Mật khẩu mới phải từ 6-20 ký tự")
    private String password;
    @NotBlank(message = "Không được để trống.")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email không đúng định dạng (Ví dụ: employee-email@email.com).")
    private String email;
    @Size(max = 100, message = "Tên nhân viên không được quá 30 ký tự.")
    @NotBlank(message = "Tên người dùng không được để trống")
    @Pattern(regexp = "^[a-zA-ZÀ-ỹ\\s]*$", message = "Tên người dùng không thể chứa ký tự đặc biệt và không thể chứa số")
    private String name;
    @NotBlank(message = "Địa chỉ không được để trống")
    @Pattern(regexp = "^[0-9]+[a-z0-ZÀ-ỹ\\s]+[a-zA-ZÀ-ỹ\\s-]*$", message = "Địa chỉ chưa đúng định dạng (Ví dụ: 123 Nam Cao - Đà Nẵng)")
    private String address;
    @NotBlank(message = "Số điện thoại không được để trống.")
    @Pattern(regexp = "^(0|\\+84)\\d{9}$", message = "Số điện thoại không đúng định dạng (Ví dụ: +84937110xxx / 0937110xxx).")
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
