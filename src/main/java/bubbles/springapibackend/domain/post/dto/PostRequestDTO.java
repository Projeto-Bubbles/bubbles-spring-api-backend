package bubbles.springapibackend.domain.post.dto;

import lombok.Data;

@Data
public class PostRequestDTO {
    private String content;
    private String author;
    private String bubble;
}


