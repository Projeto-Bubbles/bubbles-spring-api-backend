package bubbles.springapibackend.api.controller.participation;

import bubbles.springapibackend.domain.participation.dto.ParticipationInfoDTO;
import bubbles.springapibackend.domain.participation.dto.ParticipationResponseDTO;
import bubbles.springapibackend.service.participation.ParticipationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/participation")
public class ParticipationController {
    private final ParticipationService participationService;

    @Autowired
    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @Operation(summary = "Pegar todas as participações em eventos",
            description = "Retornas todos os participantes de um evento")
    @GetMapping()
    public ResponseEntity<List<ParticipationResponseDTO>> getAllParticipants() {
        List<ParticipationResponseDTO> participations = participationService.getAllParticipation();

        if (participations.isEmpty()) return ResponseEntity.noContent().build();

        List<ParticipationResponseDTO> participationResponseDTOS = participations.stream()
                .sorted(Comparator.comparing(ParticipationResponseDTO::getIdParticipation))
                .toList();
        return ResponseEntity.ok(participationResponseDTOS);
    }

    @Operation(summary = "Mostrar os próximos 5 eventos de um usuário especifíco",
            description = "Mostra os próximos 5 eventos de um usuário específico")
    @GetMapping("/{participant}")
    public ResponseEntity<List<ParticipationInfoDTO>> getNext5EventsByIdUser(@PathVariable Integer participant) {
        List<ParticipationInfoDTO> participations = participationService.getNext5EventsByIdUser(participant);

        if (participations.isEmpty()) return ResponseEntity.noContent().build();

        List<ParticipationInfoDTO> participationResponseDTOS = participations.stream()
                .sorted(Comparator.comparing(ParticipationInfoDTO::getIdParticipation))
                .collect(Collectors.toList());
        return ResponseEntity.ok(participationResponseDTOS);
    }
}
