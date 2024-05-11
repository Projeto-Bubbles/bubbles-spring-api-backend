package bubbles.springapibackend.service.participation;

import bubbles.springapibackend.domain.participation.dto.ParticipationInfoDTO;
import bubbles.springapibackend.domain.participation.dto.ParticipationResponseDTO;
import bubbles.springapibackend.domain.participation.mapper.ParticipationMapper;
import bubbles.springapibackend.domain.participation.repository.ParticipationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ParticipationService {
    private final ParticipationRepository participationRepository;
    private final ParticipationMapper participationMapper;

    public List<ParticipationResponseDTO> getAllParticipation() {
        return participationRepository.findAll().stream()
                .map(participationMapper::toDTO).collect(Collectors.toList());
    }

    public List<ParticipationInfoDTO> getNext5EventsByIdUser(Integer idParticipant) {
        return participationRepository.findNext5EventsByIdUser(idParticipant).stream()
                .map(participationMapper::toInfoDTO).collect(Collectors.toList());
    }
}
