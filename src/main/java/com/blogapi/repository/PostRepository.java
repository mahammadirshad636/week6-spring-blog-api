package com.blogapi.repository;

import com.blogapi.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    List<Post> findByCategoryId(Long categoryId);
    
    Page<Post> findByCategoryId(Long categoryId, Pageable pageable);
    
    List<Post> findByAuthor(String author);
    
    Page<Post> findByAuthorContainingIgnoreCase(String author, Pageable pageable);
    
    Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
            String title, String content, Pageable pageable);
    
    @Query("SELECT p FROM Post p WHERE p.category.id = :categoryId ORDER BY p.createdAt DESC")
    List<Post> findLatestPostsByCategory(@Param("categoryId") Long categoryId);
    
    long countByCategoryId(Long categoryId);
}
