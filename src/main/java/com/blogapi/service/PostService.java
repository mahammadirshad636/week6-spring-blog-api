package com.blogapi.service;

import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.model.dto.PostRequest;
import com.blogapi.model.dto.PostResponse;
import com.blogapi.model.entity.Category;
import com.blogapi.model.entity.Post;
import com.blogapi.repository.CategoryRepository;
import com.blogapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PostService {
    
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    
    @Transactional(readOnly = true)
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        log.info("Fetching all posts with pagination: {}", pageable);
        return postRepository.findAll(pageable)
                .map(this::mapToResponse);
    }
    
    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id) {
        log.info("Fetching post with id: {}", id);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        return mapToResponse(post);
    }
    
    public PostResponse createPost(PostRequest postRequest) {
        log.info("Creating new post with title: {}", postRequest.getTitle());
        
        // Validate category exists
        Category category = categoryRepository.findById(postRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + postRequest.getCategoryId()));
        
        // Create new post
        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .author(postRequest.getAuthor())
                .category(category)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        Post savedPost = postRepository.save(post);
        log.info("Post created successfully with id: {}", savedPost.getId());
        return mapToResponse(savedPost);
    }
    
    public PostResponse updatePost(Long id, PostRequest postRequest) {
        log.info("Updating post with id: {}", id);
        
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        
        // Update category if changed
        if (!post.getCategory().getId().equals(postRequest.getCategoryId())) {
            Category category = categoryRepository.findById(postRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Category not found with id: " + postRequest.getCategoryId()));
            post.setCategory(category);
        }
        
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setAuthor(postRequest.getAuthor());
        post.setUpdatedAt(LocalDateTime.now());
        
        Post updatedPost = postRepository.save(post);
        log.info("Post updated successfully with id: {}", updatedPost.getId());
        return mapToResponse(updatedPost);
    }
    
    public void deletePost(Long id) {
        log.info("Deleting post with id: {}", id);
        
        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
        log.info("Post deleted successfully with id: {}", id);
    }
    
    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByCategory(Long categoryId) {
        log.info("Fetching posts for category: {}", categoryId);
        
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }
        
        return postRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Page<PostResponse> searchPosts(String searchTerm, Pageable pageable) {
        log.info("Searching posts with term: {}", searchTerm);
        return postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
                searchTerm, searchTerm, pageable)
                .map(this::mapToResponse);
    }
    
    private PostResponse mapToResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .categoryId(post.getCategory().getId())
                .categoryName(post.getCategory().getName())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
