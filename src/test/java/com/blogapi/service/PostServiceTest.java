package com.blogapi.service;

import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.model.dto.PostRequest;
import com.blogapi.model.dto.PostResponse;
import com.blogapi.model.entity.Category;
import com.blogapi.model.entity.Post;
import com.blogapi.repository.CategoryRepository;
import com.blogapi.repository.PostRepository;
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
public class PostServiceTest {
    
    @Mock
    private PostRepository postRepository;
    
    @Mock
    private CategoryRepository categoryRepository;
    
    @InjectMocks
    private PostService postService;
    
    private Category category;
    private Post post;
    private PostRequest postRequest;
    
    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1L)
                .name("Technology")
                .description("Tech posts")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        post = Post.builder()
                .id(1L)
                .title("Spring Boot Guide")
                .content("Complete guide to Spring Boot")
                .author("John Doe")
                .category(category)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        postRequest = PostRequest.builder()
                .title("Spring Boot Guide")
                .content("Complete guide to Spring Boot")
                .author("John Doe")
                .categoryId(1L)
                .build();
    }
    
    @Test
    void testGetAllPosts() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> postPage = new PageImpl<>(Collections.singletonList(post));
        when(postRepository.findAll(pageable)).thenReturn(postPage);
        
        // Act
        Page<PostResponse> result = postService.getAllPosts(pageable);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Spring Boot Guide");
        verify(postRepository, times(1)).findAll(pageable);
    }
    
    @Test
    void testGetPostById_Success() {
        // Arrange
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        
        // Act
        PostResponse result = postService.getPostById(1L);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Spring Boot Guide");
        verify(postRepository, times(1)).findById(1L);
    }
    
    @Test
    void testGetPostById_NotFound() {
        // Arrange
        when(postRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> postService.getPostById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Post not found");
    }
    
    @Test
    void testCreatePost_Success() {
        // Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(postRepository.save(any(Post.class))).thenReturn(post);
        
        // Act
        PostResponse result = postService.createPost(postRequest);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Spring Boot Guide");
        assertThat(result.getCategoryName()).isEqualTo("Technology");
        verify(categoryRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).save(any(Post.class));
    }
    
    @Test
    void testCreatePost_CategoryNotFound() {
        // Arrange
        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> postService.createPost(
                PostRequest.builder()
                        .title("Test")
                        .content("Test content")
                        .author("Test Author")
                        .categoryId(999L)
                        .build()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category not found");
    }
    
    @Test
    void testDeletePost_Success() {
        // Arrange
        when(postRepository.existsById(1L)).thenReturn(true);
        
        // Act
        postService.deletePost(1L);
        
        // Assert
        verify(postRepository, times(1)).deleteById(1L);
    }
    
    @Test
    void testDeletePost_NotFound() {
        // Arrange
        when(postRepository.existsById(999L)).thenReturn(false);
        
        // Act & Assert
        assertThatThrownBy(() -> postService.deletePost(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Post not found");
    }
}
