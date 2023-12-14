package bubbles.springapibackend.domain.bubble.dto;

import bubbles.springapibackend.api.enums.Category;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class BubbleDTO {
    private Integer id;
    private String headline;
    private String explanation;
    private LocalDate creationDate;
    private Category category;
    private String creator;
}
