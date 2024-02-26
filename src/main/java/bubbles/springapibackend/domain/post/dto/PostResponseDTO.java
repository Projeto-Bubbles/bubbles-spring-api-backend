package bubbles.springapibackend.domain.post.dto;

import bubbles.springapibackend.domain.comment.dto.CommentResponseDTO;
import bubbles.springapibackend.domain.user.User;
import bubbles.springapibackend.domain.user.dto.UserBubbleDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponseDTO {
    private Integer id;
    private LocalDateTime moment;
    private String contents;
    private UserBubbleDTO author;
    private String bubble;
    private List<CommentResponseDTO> comments;
}