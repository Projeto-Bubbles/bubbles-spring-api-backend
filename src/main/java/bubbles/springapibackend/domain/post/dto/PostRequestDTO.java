package bubbles.springapibackend.domain.post.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostRequestDTO {
    private LocalDateTime moment;
    private String image;
    private String contents;
    private Integer idAuthor;
    private Integer idBubble;
}
