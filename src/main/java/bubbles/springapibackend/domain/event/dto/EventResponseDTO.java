package bubbles.springapibackend.domain.event.dto;

import bubbles.springapibackend.domain.bubble.dto.BubbleResponseDTO;
import bubbles.springapibackend.domain.user.dto.UserInfoDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class EventResponseDTO {
    private Integer idEvent;
    private String title;
    private LocalDateTime moment;
    private Integer duration;
    private UserInfoDTO organizer;
    private BubbleResponseDTO bubble;
}
