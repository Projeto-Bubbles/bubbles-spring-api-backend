package bubbles.springapibackend.domain.bubble.dto;

import bubbles.springapibackend.api.enums.Category;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BubbleDTO {
    private Integer id;
    private String title;
    private String description;
    private LocalDate creationDate;
    private Category category;
    private String creator;
}
