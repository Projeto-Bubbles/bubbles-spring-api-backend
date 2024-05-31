package bubbles.springapibackend.service.participation;

import bubbles.springapibackend.domain.event.Event;
import bubbles.springapibackend.domain.event.dto.EventResponseDTO;
import bubbles.springapibackend.domain.event.mapper.EventMapper;
import bubbles.springapibackend.domain.participation.Participation;
import bubbles.springapibackend.domain.participation.dto.ParticipationInfoDTO;
import bubbles.springapibackend.domain.participation.dto.ParticipationRequestDTO;
import bubbles.springapibackend.domain.participation.dto.ParticipationResponseDTO;
import bubbles.springapibackend.domain.participation.mapper.ParticipationMapper;
import bubbles.springapibackend.domain.participation.repository.ParticipationRepository;
import bubbles.springapibackend.domain.user.User;
import bubbles.springapibackend.service.event.EventService;
import bubbles.springapibackend.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ParticipationService {
    private final ParticipationRepository participationRepository;
    private final ParticipationMapper participationMapper;
    private final UserService userService;
    private final EventService eventService;
    private final EventMapper eventMapper;

    public List<ParticipationResponseDTO> getAllParticipation() {
        return participationRepository.findAll().stream()
                .map(participationMapper::toDTO).collect(Collectors.toList());
    }

    public List<EventResponseDTO> getEventsByIdUser(Integer userId) {
        List<Participation> participations = participationRepository.findByUserIdUser(userId);
        return participations.stream()
                .map(participation -> participation.getEvent())
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ParticipationInfoDTO> getNext5EventsByIdUser(Integer idParticipant) {
        return participationRepository.findNext5EventsByIdUser(idParticipant).stream()
                .map(participationMapper::toInfoDTO).collect(Collectors.toList());
    }

    public ParticipationResponseDTO createNewParticipation(ParticipationRequestDTO newParticipationDTO) {
        if (newParticipationDTO.getIdEvent() == null || newParticipationDTO.getIdUser() == null) {
            throw new IllegalArgumentException("fkUser ou fkEvent não pode ser nula");
        }

        User user = userService.getUserById(newParticipationDTO.getIdUser());
        Event event = eventService.getEventById(newParticipationDTO.getIdEvent());

        Participation newParticipation = new Participation();
        newParticipation.setUser(user);
        newParticipation.setEvent(event);

        return participationMapper.toDTO(participationRepository.save(newParticipation));
    }

    public boolean deleteParticipantById(Integer id) {
        Optional<Participation> existingParticipantOpt = participationRepository.findById(id);
        if (existingParticipantOpt.isPresent()) {
            participationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean deleteParticipantByUserIdAndEventId(Integer userId, Integer eventId) {
        Optional<Participation> existingParticipantOpt = participationRepository.findByUserIdUserAndEventIdEvent(userId, eventId);
        if (existingParticipantOpt.isPresent()) {
            participationRepository.delete(existingParticipantOpt.get());
            return true;
        }
        return false;
    }
}
