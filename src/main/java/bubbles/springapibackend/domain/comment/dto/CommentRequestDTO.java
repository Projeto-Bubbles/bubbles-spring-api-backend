package bubbles.springapibackend.domain.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentRequestDTO {
    private Integer authorId;
    private LocalDateTime dateTime;
    private String content;
}