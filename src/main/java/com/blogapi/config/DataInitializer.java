package com.blogapi.config;

import com.blogapi.model.entity.Category;
import com.blogapi.model.entity.Post;
import com.blogapi.model.entity.Comment;
import com.blogapi.repository.CategoryRepository;
import com.blogapi.repository.PostRepository;
import com.blogapi.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@Slf4j
public class DataInitializer {
    
    @Bean
    public CommandLineRunner initializeData(
            CategoryRepository categoryRepository,
            PostRepository postRepository,
            CommentRepository commentRepository) {
        
        return args -> {
            // Check if data already exists
            if (categoryRepository.count() > 0) {
                log.info("Sample data already exists, skipping initialization");
                return;
            }
            
            log.info("Initializing sample data...");
            
            // Create Categories
            Category technology = Category.builder()
                    .name("Technology")
                    .description("Latest technology trends and news")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            Category programming = Category.builder()
                    .name("Programming")
                    .description("Programming languages, frameworks, and best practices")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            Category webDevelopment = Category.builder()
                    .name("Web Development")
                    .description("Web development tutorials and guides")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            technology = categoryRepository.save(technology);
            programming = categoryRepository.save(programming);
            webDevelopment = categoryRepository.save(webDevelopment);
            
            log.info("✅ Created 3 categories");
            
            // Create Posts
            Post post1 = Post.builder()
                    .title("Getting Started with Spring Boot")
                    .content("Spring Boot makes it easy to create stand-alone, production-grade Spring based applications that you can run. We take an opinionated view of the Spring platform and third-party libraries so you can get started with minimum fuss.")
                    .author("John Doe")
                    .category(technology)
                    .createdAt(LocalDateTime.now().minusDays(5))
                    .updatedAt(LocalDateTime.now().minusDays(5))
                    .build();
            
            Post post2 = Post.builder()
                    .title("Java Best Practices in 2024")
                    .content("Java continues to evolve with new features and best practices. In this guide, we explore the top Java best practices that experienced developers follow to write clean, maintainable, and efficient code.")
                    .author("Jane Smith")
                    .category(programming)
                    .createdAt(LocalDateTime.now().minusDays(4))
                    .updatedAt(LocalDateTime.now().minusDays(4))
                    .build();
            
            Post post3 = Post.builder()
                    .title("REST API Design Principles")
                    .content("Building robust REST APIs requires understanding key principles. Learn about resource design, HTTP methods, status codes, versioning, and other crucial aspects of REST API design.")
                    .author("Bob Johnson")
                    .category(webDevelopment)
                    .createdAt(LocalDateTime.now().minusDays(3))
                    .updatedAt(LocalDateTime.now().minusDays(3))
                    .build();
            
            Post post4 = Post.builder()
                    .title("Database Design Fundamentals")
                    .content("A well-designed database is crucial for any application. This article covers normalization, indexing, query optimization, and other essential database design concepts.")
                    .author("Alice Brown")
                    .category(programming)
                    .createdAt(LocalDateTime.now().minusDays(2))
                    .updatedAt(LocalDateTime.now().minusDays(2))
                    .build();
            
            Post post5 = Post.builder()
                    .title("Microservices Architecture Guide")
                    .content("Microservices have become a popular architectural pattern. Explore how to design, implement, and deploy microservices effectively with best practices and common pitfalls to avoid.")
                    .author("Charlie Wilson")
                    .category(technology)
                    .createdAt(LocalDateTime.now().minusDays(1))
                    .updatedAt(LocalDateTime.now().minusDays(1))
                    .build();
            
            post1 = postRepository.save(post1);
            post2 = postRepository.save(post2);
            post3 = postRepository.save(post3);
            post4 = postRepository.save(post4);
            post5 = postRepository.save(post5);
            
            log.info("✅ Created 5 sample blog posts");
            
            // Create Comments
            Comment comment1 = Comment.builder()
                    .content("Great tutorial! Very helpful for beginners starting with Spring Boot.")
                    .author("Mike Taylor")
                    .post(post1)
                    .approved(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            Comment comment2 = Comment.builder()
                    .content("Excellent explanation of REST principles. Will definitely refer to this article.")
                    .author("Sarah Davis")
                    .post(post3)
                    .approved(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            Comment comment3 = Comment.builder()
                    .content("Thanks for the comprehensive guide on microservices!")
                    .author("Tom Anderson")
                    .post(post5)
                    .approved(false)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            commentRepository.save(comment1);
            commentRepository.save(comment2);
            commentRepository.save(comment3);
            
            log.info("✅ Created 3 sample comments");
            
            log.info("\n" +
                    "╔══════════════════════════════════════════════╗\n" +
                    "║  📝 BLOG MANAGEMENT API - STARTED             ║\n" +
                    "╚══════════════════════════════════════════════╝\n" +
                    "\n" +
                    "🚀 Application Started Successfully!\n" +
                    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                    "\n" +
                    "📊 Database Information:\n" +
                    "   • Categories: 3\n" +
                    "   • Posts: 5\n" +
                    "   • Comments: 3\n" +
                    "\n" +
                    "🔗 Available Endpoints:\n" +
                    "   GET    http://localhost:8080/api/posts\n" +
                    "   GET    http://localhost:8080/api/categories\n" +
                    "   POST   http://localhost:8080/api/posts\n" +
                    "   POST   http://localhost:8080/api/categories\n" +
                    "\n" +
                    "📚 Documentation:\n" +
                    "   • Swagger UI: http://localhost:8080/swagger-ui.html\n" +
                    "   • API Docs: http://localhost:8080/api-docs\n" +
                    "   • H2 Console: http://localhost:8080/h2-console\n" +
                    "\n" +
                    "╔══════════════════════════════════════════════╗\n");
        };
    }
}
