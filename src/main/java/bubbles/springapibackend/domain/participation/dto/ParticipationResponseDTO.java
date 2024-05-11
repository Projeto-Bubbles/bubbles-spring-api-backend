package bubbles.springapibackend.domain.participation.dto;

import bubbles.springapibackend.domain.event.dto.EventResponseDTO;
import bubbles.springapibackend.domain.user.dto.UserInfoDTO;
import lombok.Data;

@Data
public class ParticipationResponseDTO {
    Integer idParticipation;
    UserInfoDTO participant;
    EventResponseDTO event;
}
