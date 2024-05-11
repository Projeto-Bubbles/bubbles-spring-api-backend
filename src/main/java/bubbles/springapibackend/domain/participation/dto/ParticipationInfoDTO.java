package bubbles.springapibackend.domain.participation.dto;

import bubbles.springapibackend.domain.event.dto.EventSimpleDTO;
import lombok.Data;

@Data
public class ParticipationInfoDTO {
    private Integer idParticipation;
    private EventSimpleDTO event;
}
