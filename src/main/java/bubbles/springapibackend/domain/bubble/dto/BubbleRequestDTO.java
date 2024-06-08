package bubbles.springapibackend.domain.bubble.dto;

import bubbles.springapibackend.api.enums.Category;
import lombok.Data;

@Data
public class BubbleRequestDTO {
    private String title;
    private String explanation;
    private Category category;
    private Integer creator;
    private String image;
}
