package bubbles.springapibackend.domain.event.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EventOnlineDTO extends EventDTO{
    private String platform;
    private String url;
}
