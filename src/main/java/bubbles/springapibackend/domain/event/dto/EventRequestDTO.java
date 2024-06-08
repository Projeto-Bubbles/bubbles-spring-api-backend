package bubbles.springapibackend.domain.event.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRequestDTO {
    private String title;
    private LocalDateTime dateTime;
    private Integer duration;
    private String image;
    private Integer idCreator;
    private Integer idBubble;
}
