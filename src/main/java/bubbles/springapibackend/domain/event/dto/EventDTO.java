package bubbles.springapibackend.domain.event.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class EventDTO {
    private Integer id;
    private String title;
    private LocalDateTime moment;
    private Integer duration;
    private String author;
    private String bubble;
}
