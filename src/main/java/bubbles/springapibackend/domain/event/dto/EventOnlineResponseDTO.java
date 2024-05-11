package bubbles.springapibackend.domain.event.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EventOnlineResponseDTO extends EventResponseDTO {
    private String platform;
    private String link;
}
