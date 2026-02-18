package com.blogapi.service;

import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.model.dto.CategoryRequest;
import com.blogapi.model.dto.CategoryResponse;
import com.blogapi.model.entity.Category;
import com.blogapi.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    
    @Mock
    private CategoryRepository categoryRepository;
    
    @InjectMocks
    private CategoryService categoryService;
    
    private Category category;
    private CategoryRequest categoryRequest;
    
    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1L)
                .name("Technology")
                .description("Tech related posts")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        categoryRequest = CategoryRequest.builder()
                .name("Technology")
                .description("Tech related posts")
                .build();
    }
    
    @Test
    void testGetAllCategories() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> categoryPage = new PageImpl<>(Collections.singletonList(category));
        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        
        // Act
        Page<CategoryResponse> result = categoryService.getAllCategories(pageable);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Technology");
        verify(categoryRepository, times(1)).findAll(pageable);
    }
    
    @Test
    void testGetCategoryById_Success() {
        // Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        
        // Act
        CategoryResponse result = categoryService.getCategoryById(1L);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Technology");
        verify(categoryRepository, times(1)).findById(1L);
    }
    
    @Test
    void testGetCategoryById_NotFound() {
        // Arrange
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> categoryService.getCategoryById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category not found");
    }
    
    @Test
    void testCreateCategory_Success() {
        // Arrange
        when(categoryRepository.existsByName("Technology")).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        
        // Act
        CategoryResponse result = categoryService.createCategory(categoryRequest);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Technology");
        verify(categoryRepository, times(1)).existsByName("Technology");
        verify(categoryRepository, times(1)).save(any(Category.class));
    }
    
    @Test
    void testCreateCategory_AlreadyExists() {
        // Arrange
        when(categoryRepository.existsByName("Technology")).thenReturn(true);
        
        // Act & Assert
        assertThatThrownBy(() -> categoryService.createCategory(categoryRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already exists");
    }
    
    @Test
    void testDeleteCategory_Success() {
        // Arrange
        category.setPosts(Collections.emptyList());
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        
        // Act
        categoryService.deleteCategory(1L);
        
        // Assert
        verify(categoryRepository, times(1)).deleteById(1L);
    }
    
    @Test
    void testDeleteCategory_NotFound() {
        // Arrange
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> categoryService.deleteCategory(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category not found");
    }
}
