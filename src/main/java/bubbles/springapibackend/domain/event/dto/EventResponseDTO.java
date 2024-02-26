package bubbles.springapibackend.domain.event.dto;

import bubbles.springapibackend.domain.user.dto.UserBubbleDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class EventResponseDTO {
    private Integer id;
    private String title;
    private LocalDateTime dateTime;
    private Integer duration;
    private UserBubbleDTO organizer;
    private Integer idBubble;
}
