package bubbles.springapibackend.domain.event.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventSimpleDTO {
    private LocalDateTime moment;
    private String title;
    private String bubbleName;
    private String image;
}
