package bubbles.springapibackend.domain.bubble.dto;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.user.dto.UserInfoDTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BubbleResponseDTO {
    private Integer idBubble;
    private String title;
    private String explanation;
    private LocalDate creationDate;
    private Category category;
    private UserInfoDTO creator;
}
