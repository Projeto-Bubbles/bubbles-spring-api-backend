package bubbles.springapibackend.domain.post.dto;

import bubbles.springapibackend.domain.comment.dto.CommentResponseDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponseDTO {
    private Integer id;
    private LocalDateTime dateTime;
    private String content;
    private String author;
    private Integer bubbleId;
    private List<CommentResponseDTO> comments;
}