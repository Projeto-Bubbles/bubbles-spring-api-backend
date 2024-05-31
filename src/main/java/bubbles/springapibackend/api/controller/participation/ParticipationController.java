package bubbles.springapibackend.api.controller.participation;

import bubbles.springapibackend.domain.event.dto.EventResponseDTO;
import bubbles.springapibackend.domain.participation.dto.ParticipationInfoDTO;
import bubbles.springapibackend.domain.participation.dto.ParticipationRequestDTO;
import bubbles.springapibackend.domain.participation.dto.ParticipationResponseDTO;
import bubbles.springapibackend.service.participation.ParticipationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EventResponseDTO>> getBubblesForUser(@PathVariable Integer userId) {
        List<EventResponseDTO> participations = participationService.getEventsByIdUser(userId);
        return ResponseEntity.ok(participations);
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

    @Operation(summary = "Criar participação", description = "Cria uma nova participação.")
    @PostMapping("/create")
    public ResponseEntity<ParticipationResponseDTO> createNewParticipation(
            @Validated @RequestBody ParticipationRequestDTO newParticipationDTO) {
        ParticipationResponseDTO newParticipation = participationService.createNewParticipation(newParticipationDTO);
        return new ResponseEntity<>(newParticipation, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Participant by ID",
            description = "Delete an participant by its unique ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipantById(
            @Parameter(description = "Event ID") @PathVariable Integer id) {
        boolean deleted = participationService.deleteParticipantById(id);
        if (deleted) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete Partipant by User ID and event ID",
            description = "Delete a Participant by its user ID and event ID.")
    @DeleteMapping("/{userId}/{eventId}")
    public ResponseEntity<Void> deleteParticpantById(
            @Parameter(description = "User ID") @PathVariable Integer userId,
            @Parameter(description = "Event ID") @PathVariable Integer eventId) {
        boolean deleted = participationService.deleteParticipantByUserIdAndEventId(userId, eventId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
