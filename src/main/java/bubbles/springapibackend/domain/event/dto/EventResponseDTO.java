package bubbles.springapibackend.domain.event.dto;

import bubbles.springapibackend.domain.user.dto.UserInfoDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class EventResponseDTO {
    private Integer id;
    private String title;
    private LocalDateTime dateTime;
    private Integer duration;
    private UserInfoDTO organizer;
    private Integer idBubble;
}