package bubbles.springapibackend.domain.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDTO {
    private Integer id;
    private String author;
    private LocalDateTime dateTime;
    private String content;
}