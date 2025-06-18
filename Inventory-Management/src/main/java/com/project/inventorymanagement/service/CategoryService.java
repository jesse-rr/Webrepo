package com.project.inventorymanagement.service;

import com.project.inventorymanagement.model.Category;
import com.project.inventorymanagement.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void addCategory(String name, String description) {
        categoryRepository.save(Category.builder()
                .name(name)
                .description(description)
                .build()
        );
    }

    public void removeCategory(List<Long> categoryIds) {
        categoryRepository.deleteAllById(categoryIds);
    }

    public void updateCategory(Long categoryId, String name, String description) {
        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("CATEGORY NOT FOUND WITH ID :: "+categoryId));
        category.setName(name);
        category.setDescription(description);
        categoryRepository.save(category);
    }

    public List<Category> getCategoriesByIds(List<Long> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }
}
