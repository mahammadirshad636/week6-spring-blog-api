package com.blogapi.service;

import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.model.dto.CommentRequest;
import com.blogapi.model.dto.CommentResponse;
import com.blogapi.model.entity.Comment;
import com.blogapi.model.entity.Post;
import com.blogapi.repository.CommentRepository;
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
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByPostId(Long postId) {
        log.info("Fetching comments for post: {}", postId);
        
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found with id: " + postId);
        }
        
        return commentRepository.findByPostId(postId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Page<CommentResponse> getCommentsByPostIdWithPagination(Long postId, Pageable pageable) {
        log.info("Fetching comments for post with pagination: {}", postId);
        
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found with id: " + postId);
        }
        
        return commentRepository.findByPostId(postId, pageable)
                .map(this::mapToResponse);
    }
    
    @Transactional(readOnly = true)
    public List<CommentResponse> getApprovedCommentsByPostId(Long postId) {
        log.info("Fetching approved comments for post: {}", postId);
        
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found with id: " + postId);
        }
        
        return commentRepository.findByPostIdAndApproved(postId, true)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public CommentResponse getCommentById(Long commentId) {
        log.info("Fetching comment with id: {}", commentId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));
        return mapToResponse(comment);
    }
    
    public CommentResponse addCommentToPost(Long postId, CommentRequest commentRequest) {
        log.info("Adding comment to post: {}", postId);
        
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        
        Comment comment = Comment.builder()
                .content(commentRequest.getContent())
                .author(commentRequest.getAuthor())
                .post(post)
                .approved(false)  // Comments need moderation by default
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        Comment savedComment = commentRepository.save(comment);
        log.info("Comment added successfully to post {} with id: {}", postId, savedComment.getId());
        return mapToResponse(savedComment);
    }
    
    public CommentResponse updateComment(Long commentId, CommentRequest commentRequest) {
        log.info("Updating comment with id: {}", commentId);
        
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));
        
        comment.setContent(commentRequest.getContent());
        comment.setAuthor(commentRequest.getAuthor());
        comment.setUpdatedAt(LocalDateTime.now());
        
        Comment updatedComment = commentRepository.save(comment);
        log.info("Comment updated successfully with id: {}", updatedComment.getId());
        return mapToResponse(updatedComment);
    }
    
    public void deleteComment(Long commentId) {
        log.info("Deleting comment with id: {}", commentId);
        
        if (!commentRepository.existsById(commentId)) {
            throw new ResourceNotFoundException("Comment not found with id: " + commentId);
        }
        
        commentRepository.deleteById(commentId);
        log.info("Comment deleted successfully with id: {}", commentId);
    }
    
    public CommentResponse approveComment(Long commentId) {
        log.info("Approving comment with id: {}", commentId);
        
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));
        
        comment.setApproved(true);
        comment.setUpdatedAt(LocalDateTime.now());
        
        Comment approvedComment = commentRepository.save(comment);
        log.info("Comment approved successfully with id: {}", approvedComment.getId());
        return mapToResponse(approvedComment);
    }
    
    public CommentResponse rejectComment(Long commentId) {
        log.info("Rejecting comment with id: {}", commentId);
        
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + commentId));
        
        comment.setApproved(false);
        comment.setUpdatedAt(LocalDateTime.now());
        
        Comment rejectedComment = commentRepository.save(comment);
        log.info("Comment rejected successfully with id: {}", rejectedComment.getId());
        return mapToResponse(rejectedComment);
    }
    
    private CommentResponse mapToResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(comment.getAuthor())
                .postId(comment.getPost().getId())
                .approved(comment.getApproved())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}
