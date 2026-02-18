package com.blogapi.repository;

import com.blogapi.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findByPostId(Long postId);
    
    Page<Comment> findByPostId(Long postId, Pageable pageable);
    
    List<Comment> findByPostIdAndApproved(Long postId, Boolean approved);
    
    Page<Comment> findByPostIdAndApproved(Long postId, Boolean approved, Pageable pageable);
    
    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.approved = true ORDER BY c.createdAt DESC")
    List<Comment> findApprovedCommentsByPostId(@Param("postId") Long postId);
    
    long countByPostId(Long postId);
    
    long countByPostIdAndApproved(Long postId, Boolean approved);
}
