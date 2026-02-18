package com.blogapi.controller;

import com.blogapi.model.dto.ApiResponse;
import com.blogapi.model.dto.PostRequest;
import com.blogapi.model.dto.PostResponse;
import com.blogapi.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "Blog Post Management APIs")
@Slf4j
public class PostController {
    
    private final PostService postService;
    
    public PostController(PostService postService) {
        this.postService = postService;
    }
    
    @GetMapping
    @Operation(summary = "Get all posts", description = "Retrieve all blog posts with pagination and sorting")
    public ResponseEntity<ApiResponse<Page<PostResponse>>> getAllPosts(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("Fetching all posts");
        Page<PostResponse> posts = postService.getAllPosts(pageable);
        return ResponseEntity.ok(
                ApiResponse.success("Posts retrieved successfully", posts)
        );
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get post by ID", description = "Retrieve a specific blog post by its ID")
    public ResponseEntity<ApiResponse<PostResponse>> getPostById(@PathVariable Long id) {
        log.info("Fetching post with id: {}", id);
        PostResponse post = postService.getPostById(id);
        return ResponseEntity.ok(
                ApiResponse.success("Post retrieved successfully", post)
        );
    }
    
    @PostMapping
    @Operation(summary = "Create new post", description = "Create a new blog post")
    public ResponseEntity<ApiResponse<PostResponse>> createPost(@Valid @RequestBody PostRequest postRequest) {
        log.info("Creating new post");
        PostResponse createdPost = postService.createPost(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.created("Post created successfully", createdPost)
        );
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update post", description = "Update an existing blog post")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostRequest postRequest) {
        log.info("Updating post with id: {}", id);
        PostResponse updatedPost = postService.updatePost(id, postRequest);
        return ResponseEntity.ok(
                ApiResponse.success("Post updated successfully", updatedPost)
        );
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete post", description = "Delete a blog post by ID")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id) {
        log.info("Deleting post with id: {}", id);
        postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get posts by category", description = "Retrieve all posts in a specific category")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPostsByCategory(@PathVariable Long categoryId) {
        log.info("Fetching posts for category: {}", categoryId);
        List<PostResponse> posts = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(
                ApiResponse.success("Posts retrieved successfully", posts)
        );
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search posts", description = "Search blog posts by title or content")
    public ResponseEntity<ApiResponse<Page<PostResponse>>> searchPosts(
            @RequestParam String searchTerm,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("Searching posts with term: {}", searchTerm);
        Page<PostResponse> posts = postService.searchPosts(searchTerm, pageable);
        return ResponseEntity.ok(
                ApiResponse.success("Posts found", posts)
        );
    }
}
