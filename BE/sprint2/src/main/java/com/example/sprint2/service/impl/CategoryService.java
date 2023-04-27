package com.example.sprint2.service.impl;

import com.example.sprint2.model.Category;
import com.example.sprint2.repository.ICategoryRepository;
import com.example.sprint2.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public List<Category> listCategory() {
        return categoryRepository.findAll();
    }
}
