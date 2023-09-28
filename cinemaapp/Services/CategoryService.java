package com.example.cinemaapp.Services;

import com.example.cinemaapp.Models.Entities.Category;
import com.example.cinemaapp.Repositories.CategoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> findCategoryByName(String name) {
       return this.categoryRepository.findByName(name);
    }

    @PostConstruct
    public void initCategories() {
        if (categoryRepository.count() > 0) return;
        this.categoryRepository.save(new Category("Horror"));
        this.categoryRepository.save(new Category("Adventure"));
        this.categoryRepository.save(new Category("Action"));
        this.categoryRepository.save(new Category("Animation"));
    }

}
