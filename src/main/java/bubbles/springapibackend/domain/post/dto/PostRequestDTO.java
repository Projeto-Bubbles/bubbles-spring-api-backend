package bubbles.springapibackend.domain.post.dto;

import bubbles.springapibackend.domain.user.User;
import lombok.Data;

@Data
public class PostRequestDTO {
    private String content;
    private Integer authorId;
    private Integer bubbleId;
}


