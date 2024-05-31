package bubbles.springapibackend.api.controller.event;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.event.Event;
import bubbles.springapibackend.domain.event.dto.*;
import bubbles.springapibackend.domain.event.mapper.EventMapper;
import bubbles.springapibackend.service.event.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @Operation(summary = "Get Available Events", description = "Returns all events for the current date or in the future.")
    @GetMapping()
    public ResponseEntity<List<EventResponseDTO>> getAvailableEvents() {
        List<EventResponseDTO> events = eventService.getAvailableEvents();

        if (events.isEmpty()) return ResponseEntity.noContent().build();

        List<EventResponseDTO> eventDTOS = events.stream()
                .sorted(Comparator.comparing(EventResponseDTO::getIdEvent))
                .collect(Collectors.toList());

        return ResponseEntity.ok(eventDTOS);
    }

    @GetMapping("/{postId}")
    @Operation(summary = "Get Available Events", description = "Returns all events for the current date or in the future.")
    public ResponseEntity<EventResponseDTO> getAvailableEvents(@PathVariable Integer postId) {
        Event event = eventService.getEventById(postId);
        EventResponseDTO eventDTO = eventMapper.toDTO(event);
        return ResponseEntity.ok(eventDTO);
    }


    @Operation(summary = "Get Events by Author", description = "Returns events authored by a specific user.")
    @GetMapping("/author")
    public ResponseEntity<List<EventResponseDTO>> getEventsByUserNickname(
            @Parameter(description = "Author's name") @RequestParam String userNickname) {
        List<EventResponseDTO> events = eventService.getEventsByUserNickname(userNickname);

        if (events.isEmpty()) return ResponseEntity.noContent().build();

        List<EventResponseDTO> eventDTOS = events.stream()
                .sorted(Comparator.comparing(EventResponseDTO::getIdEvent))
                .collect(Collectors.toList());

        return ResponseEntity.ok(eventDTOS);
    }

    @Operation(summary = "Get Events by Bubble",
            description = "Returns events associated with a specific bubble (group).")
    @GetMapping("/bubble")
    public ResponseEntity<List<EventResponseDTO>> getEventsByBubble(
            @Parameter(description = "Bubble (group) name") @RequestParam String bubbleTitle) {
        List<EventResponseDTO> events = eventService.getEventsByBubbleTitle(bubbleTitle);
        if (events.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/filtered")
    @Operation(summary = "Get Events by Category",
            description = "Returns events associated with a specific category.")
    public ResponseEntity<List<EventResponseDTO>> getEventsByCategory(
            @Parameter(description = "Event categories")
            @RequestParam(required = false, defaultValue = "") List<String> categories) {
        List<Category> categoryEnums = categories.stream().map(Category::valueOf).collect(Collectors.toList());
        List<EventResponseDTO> events = eventService.getFilteredEvents(categoryEnums);

        if (categories.isEmpty()) return getAvailableEvents();

        List<EventResponseDTO> eventDTOS = events.stream()
                .sorted(Comparator.comparing(EventResponseDTO::getIdEvent))
                .collect(Collectors.toList());

        return ResponseEntity.ok(eventDTOS);
    }

    @Operation(summary = "Create In-Person Event",
            description = "Create a new in-person event.")
    @PostMapping("/inPerson")
    public ResponseEntity<EventResponseDTO> createInPersonEvent(
            @Validated @RequestBody EventInPersonRequestDTO newEventInPerson) {
            EventResponseDTO savedEvent = eventService.createInPersonEvent(newEventInPerson);
            return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    @Operation(summary = "Create Online Event",
            description = "Create a new online event.")
    @PostMapping("/online")
    public ResponseEntity<EventResponseDTO> createOnlineEvent(
            @Validated @RequestBody EventOnlineRequestDTO newEventOnline) {
        EventResponseDTO createdEvent = eventService.createOnlineEvent(newEventOnline);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @Operation(summary = "Edit In-Person Event", description = "Edit an existing in-person event.")
    @PatchMapping("/edit/inPerson/{id}")
    public ResponseEntity<EventInPersonResponseDTO> editInPersonEvent(
            @Parameter(description = "Event ID") @PathVariable Integer id,
            @Parameter(description = "Patched in-person event JSON")
            @Validated @RequestBody EventInPersonRequestDTO updatedEventInPersonDTO) {
        EventInPersonResponseDTO editedEventInPersonDTO = eventService.editInPersonEvent(id, updatedEventInPersonDTO);
        return ResponseEntity.ok(editedEventInPersonDTO);
    }

    @Operation(summary = "Edit Online Event", description = "Edit an existing online event.")
    @PatchMapping("/edit/online/{id}")
    public ResponseEntity<EventOnlineResponseDTO> editOnlineEvent(
            @Parameter(description = "Event ID") @PathVariable Integer id,
            @Parameter(description = "Patched online event JSON")
            @Validated @RequestBody EventOnlineResponseDTO updatedEventOnlineDTO) {
        EventOnlineResponseDTO editedEventOnlineDTO = eventService.editOnlineEvent(id, updatedEventOnlineDTO);
        return ResponseEntity.ok(editedEventOnlineDTO);
    }

    @Operation(summary = "Delete Event by ID",
            description = "Delete an event by its unique ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventById(
            @Parameter(description = "Event ID") @PathVariable Integer id) {
        boolean deleted = eventService.deleteEventById(id);
        if (deleted) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
