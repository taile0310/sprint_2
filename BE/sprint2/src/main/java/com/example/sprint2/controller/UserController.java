package com.example.sprint2.controller;


import com.example.sprint2.dto.UserDTO;
import com.example.sprint2.model.User;
import com.example.sprint2.service.IProductService;
import com.example.sprint2.service.IUserService;
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

@CrossOrigin("*")
@RestController
@RequestMapping("api/admin")
public class UserController {
    @Autowired
    private IUserService userService;


    @GetMapping("/list-employee")
    public ResponseEntity<?> getListEmployee(@RequestParam(required = false, defaultValue = "0") int page,
                                             @RequestParam(required = false, defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userService.employeePage(pageable);
        if (userPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(userPage, HttpStatus.OK);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewUser(@Validated @RequestBody UserDTO userDTO,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @Validated @RequestBody UserDTO userDTO,
                                           BindingResult bindingResult) {
        User user = userService.findById(id);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(userDTO, user);
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

}
