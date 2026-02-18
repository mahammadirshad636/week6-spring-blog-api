package com.blogapi.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {
    
    @NotBlank(message = "Comment content is required")
    private String content;
    
    @NotBlank(message = "Author name is required")
    private String author;
}
