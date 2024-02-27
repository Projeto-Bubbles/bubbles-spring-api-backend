package bubbles.springapibackend.domain.comment.dto;

import bubbles.springapibackend.domain.user.dto.UserInfoDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDTO {
    private Integer idComment;
    private LocalDateTime moment;
    private String contents;
    private UserInfoDTO author;
}