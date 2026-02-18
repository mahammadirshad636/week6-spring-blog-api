package com.blogapi.controller;

import com.blogapi.model.dto.ApiResponse;
import com.blogapi.model.dto.CommentRequest;
import com.blogapi.model.dto.CommentResponse;
import com.blogapi.service.CommentService;
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
@RequestMapping("/api/posts/{postId}/comments")
@Tag(name = "Comments", description = "Blog Comment Management APIs")
@Slf4j
public class CommentController {
    
    private final CommentService commentService;
    
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    
    @GetMapping
    @Operation(summary = "Get comments by post ID", description = "Retrieve all comments for a specific blog post")
    public ResponseEntity<ApiResponse<Page<CommentResponse>>> getCommentsByPostId(
            @PathVariable Long postId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("Fetching comments for post: {}", postId);
        Page<CommentResponse> comments = commentService.getCommentsByPostIdWithPagination(postId, pageable);
        return ResponseEntity.ok(
                ApiResponse.success("Comments retrieved successfully", comments)
        );
    }
    
    @GetMapping("/approved")
    @Operation(summary = "Get approved comments", description = "Retrieve approved comments for a specific blog post")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getApprovedComments(@PathVariable Long postId) {
        log.info("Fetching approved comments for post: {}", postId);
        List<CommentResponse> comments = commentService.getApprovedCommentsByPostId(postId);
        return ResponseEntity.ok(
                ApiResponse.success("Approved comments retrieved successfully", comments)
        );
    }
    
    @PostMapping
    @Operation(summary = "Add comment to post", description = "Add a new comment to a blog post")
    public ResponseEntity<ApiResponse<CommentResponse>> addComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest commentRequest) {
        log.info("Adding comment to post: {}", postId);
        CommentResponse comment = commentService.addCommentToPost(postId, commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.created("Comment added successfully", comment)
        );
    }
    
    @PutMapping("/{commentId}")
    @Operation(summary = "Update comment", description = "Update an existing comment")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest commentRequest) {
        log.info("Updating comment: {} for post: {}", commentId, postId);
        CommentResponse updatedComment = commentService.updateComment(commentId, commentRequest);
        return ResponseEntity.ok(
                ApiResponse.success("Comment updated successfully", updatedComment)
        );
    }
    
    @DeleteMapping("/{commentId}")
    @Operation(summary = "Delete comment", description = "Delete a comment by ID")
    public ResponseEntity<ApiResponse<Void>> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId) {
        log.info("Deleting comment: {} from post: {}", commentId, postId);
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    @PutMapping("/{commentId}/approve")
    @Operation(summary = "Approve comment", description = "Approve a pending comment for moderation")
    public ResponseEntity<ApiResponse<CommentResponse>> approveComment(
            @PathVariable Long postId,
            @PathVariable Long commentId) {
        log.info("Approving comment: {} for post: {}", commentId, postId);
        CommentResponse approvedComment = commentService.approveComment(commentId);
        return ResponseEntity.ok(
                ApiResponse.success("Comment approved successfully", approvedComment)
        );
    }
    
    @PutMapping("/{commentId}/reject")
    @Operation(summary = "Reject comment", description = "Reject/unapprove a comment")
    public ResponseEntity<ApiResponse<CommentResponse>> rejectComment(
            @PathVariable Long postId,
            @PathVariable Long commentId) {
        log.info("Rejecting comment: {} for post: {}", commentId, postId);
        CommentResponse rejectedComment = commentService.rejectComment(commentId);
        return ResponseEntity.ok(
                ApiResponse.success("Comment rejected successfully", rejectedComment)
        );
    }
}
