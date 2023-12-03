package bubbles.springapibackend.domain.comment.dto;

import bubbles.springapibackend.domain.user.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentRequestDTO {
    private Integer authorId;
    private Integer postId;
    private LocalDateTime dateTime;
    private String content;
}