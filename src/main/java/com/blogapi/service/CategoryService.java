package com.blogapi.service;

import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.model.dto.CategoryRequest;
import com.blogapi.model.dto.CategoryResponse;
import com.blogapi.model.entity.Category;
import com.blogapi.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    @Transactional(readOnly = true)
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        log.info("Fetching all categories with pagination: {}", pageable);
        return categoryRepository.findAll(pageable)
                .map(this::mapToResponse);
    }
    
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        log.info("Fetching category with id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return mapToResponse(category);
    }
    
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        log.info("Creating new category: {}", categoryRequest.getName());
        
        // Check if category name already exists
        if (categoryRepository.existsByName(categoryRequest.getName())) {
            throw new IllegalArgumentException("Category with name '" + categoryRequest.getName() + "' already exists");
        }
        
        Category category = Category.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        Category savedCategory = categoryRepository.save(category);
        log.info("Category created successfully with id: {}", savedCategory.getId());
        return mapToResponse(savedCategory);
    }
    
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        log.info("Updating category with id: {}", id);
        
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        
        // Check if new name already exists (excluding current category)
        if (!category.getName().equals(categoryRequest.getName()) && 
            categoryRepository.existsByName(categoryRequest.getName())) {
            throw new IllegalArgumentException("Category with name '" + categoryRequest.getName() + "' already exists");
        }
        
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        category.setUpdatedAt(LocalDateTime.now());
        
        Category updatedCategory = categoryRepository.save(category);
        log.info("Category updated successfully with id: {}", updatedCategory.getId());
        return mapToResponse(updatedCategory);
    }
    
    public void deleteCategory(Long id) {
        log.info("Deleting category with id: {}", id);
        
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        
        // Check if category has posts
        if (!category.getPosts().isEmpty()) {
            throw new IllegalArgumentException("Cannot delete category with existing posts. Delete all posts first.");
        }
        
        categoryRepository.deleteById(id);
        log.info("Category deleted successfully with id: {}", id);
    }
    
    @Transactional(readOnly = true)
    public Page<CategoryResponse> searchCategories(String searchTerm, Pageable pageable) {
        log.info("Searching categories with term: {}", searchTerm);
        return categoryRepository.findByNameContainingIgnoreCase(searchTerm, pageable)
                .map(this::mapToResponse);
    }
    
    private CategoryResponse mapToResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}
