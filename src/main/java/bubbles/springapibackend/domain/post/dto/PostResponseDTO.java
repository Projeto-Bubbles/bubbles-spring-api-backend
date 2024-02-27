package bubbles.springapibackend.domain.post.dto;

import bubbles.springapibackend.domain.bubble.dto.BubbleInfoDTO;
import bubbles.springapibackend.domain.comment.dto.CommentResponseDTO;
import bubbles.springapibackend.domain.user.dto.UserInfoDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponseDTO {
    private Integer idPost;
    private LocalDateTime moment;
    private String contents;
    private UserInfoDTO author;
    private BubbleInfoDTO bubble;
    private List<CommentResponseDTO> comments;
}