package bubbles.springapibackend.domain.event.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EventOnlineRequestDTO extends EventRequestDTO {
    private String platform;
    private String link;
}
