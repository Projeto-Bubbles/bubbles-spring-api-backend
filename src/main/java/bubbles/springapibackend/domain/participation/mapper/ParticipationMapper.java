package bubbles.springapibackend.domain.participation.mapper;

import bubbles.springapibackend.domain.event.mapper.EventMapper;
import bubbles.springapibackend.domain.participation.Participation;
import bubbles.springapibackend.domain.participation.dto.ParticipationInfoDTO;
import bubbles.springapibackend.domain.participation.dto.ParticipationResponseDTO;
import bubbles.springapibackend.domain.user.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParticipationMapper {

    private final EventMapper eventMapper;
    private final UserMapper userMapper;

    @Autowired
    public ParticipationMapper(EventMapper eventMapper, UserMapper userMapper) {
        this.eventMapper = eventMapper;
        this.userMapper = userMapper;
    }

    public ParticipationResponseDTO toDTO(Participation participation) {
        if (participation == null || participation.getUser() == null ||
                participation.getEvent() == null) {
            throw new EntityNotFoundException("Participação, particpante ou evento null");
        }

        ParticipationResponseDTO participationResponseDTO = new ParticipationResponseDTO();
        participationResponseDTO.setIdParticipation(participation.getIdParticipant());
        participationResponseDTO.setParticipant(userMapper.toUserInfoDTO(participation.getUser()));
        participationResponseDTO.setEvent(eventMapper.toDTO(participation.getEvent()));
        return participationResponseDTO;
    }

    public ParticipationInfoDTO toInfoDTO(Participation participation) {
        if (participation == null || participation.getEvent() == null ||
                participation.getIdParticipant() == null) {
            throw new EntityNotFoundException("Participação, evento null");
        }

        ParticipationInfoDTO participationResponseDTO = new ParticipationInfoDTO();
        participationResponseDTO.setIdParticipation(participation.getIdParticipant());
        participationResponseDTO.setEvent(eventMapper.toEventSimpleDTO(participation.getEvent()));

        return participationResponseDTO;
    }
}
