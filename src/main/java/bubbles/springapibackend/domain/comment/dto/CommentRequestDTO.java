package bubbles.springapibackend.domain.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentRequestDTO {
    private LocalDateTime moment;
    private String content;
    private Integer idAuthor;
}