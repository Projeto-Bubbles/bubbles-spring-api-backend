package bubbles.springapibackend.domain.event.dto;

import lombok.Data;

@Data
public class EventOnlineDTO extends EventDTO{
    private String platform;
    private String url;
}
