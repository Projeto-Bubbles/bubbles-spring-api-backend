package bubbles.springapibackend.api.controller.event;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.event.Event;
import bubbles.springapibackend.domain.event.dto.EventDTO;
import bubbles.springapibackend.domain.event.dto.EventInPersonDTO;
import bubbles.springapibackend.domain.event.dto.EventOnlineDTO;
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
    public ResponseEntity<List<EventDTO>> getAvailableEvents() {
        List<EventDTO> events = eventService.getAvailableEvents();

        if (events.isEmpty()) return ResponseEntity.noContent().build();

        List<EventDTO> eventDTOS = events.stream()
                .sorted(Comparator.comparing(EventDTO::getId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(eventDTOS);
    }

    @GetMapping("/{postId}")
    @Operation(summary = "Get Available Events", description = "Returns all events for the current date or in the future.")
    public ResponseEntity<EventDTO> getAvailableEvents(@PathVariable Integer postId) {
        Event event = eventService.getEventById(postId);
        EventDTO eventDTO = eventMapper.toDTO(event);
        return ResponseEntity.ok(eventDTO);
    }


    @Operation(summary = "Get Events by Author", description = "Returns events authored by a specific user.")
    @GetMapping("/author")
    public ResponseEntity<List<EventDTO>> getEventsByUserNickname(
            @Parameter(description = "Author's name") @RequestParam String userNickname) {
        List<EventDTO> events = eventService.getEventsByUserNickname(userNickname);

        if (events.isEmpty()) return ResponseEntity.noContent().build();

        List<EventDTO> eventDTOS = events.stream()
                .sorted(Comparator.comparing(EventDTO::getId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(eventDTOS);
    }

    @Operation(summary = "Get Events by Bubble",
            description = "Returns events associated with a specific bubble (group).")
    @GetMapping("/bubble")
    public ResponseEntity<List<EventDTO>> getEventsByBubble(
            @Parameter(description = "Bubble (group) name") @RequestParam String bubbleTitle) {
        List<EventDTO> events = eventService.getEventsByBubbleTitle(bubbleTitle);
        if (events.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/filtered")
    @Operation(summary = "Get Events by Category",
            description = "Returns events associated with a specific category.")
    public ResponseEntity<List<EventDTO>> getEventsByCategory(
            @Parameter(description = "Event categories") @RequestParam List<String> categories) {
        List<Category> categoryEnums = categories.stream().map(Category::valueOf).collect(Collectors.toList());
        List<EventDTO> events = eventService.getFilteredEvents(categoryEnums);

        if (events.isEmpty()) return ResponseEntity.noContent().build();

        List<EventDTO> eventDTOS = events.stream()
                .sorted(Comparator.comparing(EventDTO::getId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(eventDTOS);
    }

    @Operation(summary = "Create In-Person Event",
            description = "Create a new in-person event.")
    @PostMapping("/inPerson")
    public ResponseEntity<EventDTO> createInPersonEvent(
            @Validated @RequestBody EventInPersonDTO newEvent) {
            EventDTO savedEvent = eventService.createInPersonEvent(newEvent);
            return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    @Operation(summary = "Create Online Event",
            description = "Create a new online event.")
    @PostMapping("/online")
    public ResponseEntity<EventDTO> createOnlineEvent(
            @Validated @RequestBody EventOnlineDTO newEvent) {
        EventDTO createdEvent = eventService.createOnlineEvent(newEvent);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @Operation(summary = "Edit In-Person Event", description = "Edit an existing in-person event.")
    @PatchMapping("/edit/inPerson/{id}")
    public ResponseEntity<EventInPersonDTO> editInPersonEvent(
            @Parameter(description = "Event ID") @PathVariable Integer id,
            @Parameter(description = "Patched in-person event JSON")
            @Validated @RequestBody EventInPersonDTO updatedEventInPersonDTO) {
        EventInPersonDTO editedEventInPersonDTO = eventService.editInPersonEvent(id, updatedEventInPersonDTO);
        return ResponseEntity.ok(editedEventInPersonDTO);
    }

    @Operation(summary = "Edit Online Event", description = "Edit an existing online event.")
    @PatchMapping("/edit/online/{id}")
    public ResponseEntity<EventOnlineDTO> editOnlineEvent(
            @Parameter(description = "Event ID") @PathVariable Integer id,
            @Parameter(description = "Patched online event JSON")
            @Validated @RequestBody EventOnlineDTO updatedEventOnlineDTO) {
        EventOnlineDTO editedEventOnlineDTO = eventService.editOnlineEvent(id, updatedEventOnlineDTO);
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
