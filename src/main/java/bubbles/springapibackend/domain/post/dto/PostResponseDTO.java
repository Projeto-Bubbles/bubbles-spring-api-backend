package bubbles.springapibackend.domain.post.dto;

import bubbles.springapibackend.domain.comment.dto.CommentResponseDTO;
import bubbles.springapibackend.domain.user.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponseDTO {
    private Integer id;
    private LocalDateTime date_time;
    private String content;
    private User author;
    private String bubble;
    private List<CommentResponseDTO> comments;
}