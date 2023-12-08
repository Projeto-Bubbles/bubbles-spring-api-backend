package bubbles.springapibackend.api.controller.event;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.event.Event;
import bubbles.springapibackend.domain.event.EventInPerson;
import bubbles.springapibackend.domain.event.EventOnline;
import bubbles.springapibackend.service.event.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @GetMapping()
    @Operation(summary = "Get Available Events", description = "Returns all events for the current date or in the future.")
    public ResponseEntity<List<Event>> getAvailableEvents() {
        List<Event> events = eventService.getAvailableEvents();

        if (events.isEmpty()) return ResponseEntity.noContent().build();

        Collections.sort(events, Comparator.comparing(Event::getId));

        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Available Events", description = "Returns all events for the current date or in the future.")
    public ResponseEntity<Optional<Event>> getAvailableEvents(@PathVariable int id) {
        Optional<Event> eventOpt = eventService.getEventById(id);

        if (eventOpt.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.of(Optional.ofNullable(eventOpt));
    }


    @Operation(summary = "Get Events by Author", description = "Returns events authored by a specific user.")
    @GetMapping("/author")
    public ResponseEntity<List<Event>> getEventsByAuthor(
            @Parameter(description = "Author's name") @RequestParam String author) {
        List<Event> events = eventService.getEventsByAuthor(author);
        if (events.isEmpty()) return ResponseEntity.noContent().build();
        Collections.sort(events, Comparator.comparing(Event::getId));
        return ResponseEntity.ok(events);
    }

    @Operation(summary = "Get Events by Bubble",
            description = "Returns events associated with a specific bubble (group).")
    @GetMapping("/bubble")
    public ResponseEntity<List<Event>> getEventsByBubble(
            @Parameter(description = "Bubble (group) name") @RequestParam String bubble) {
        List<Event> events = eventService.getEventsByBubble(bubble);
        if (events.isEmpty()) return ResponseEntity.noContent().build();
        Collections.sort(events, Comparator.comparing(Event::getId));
        return ResponseEntity.ok(events);
    }

    @GetMapping("/filtered")
    @Operation(summary = "Get Events by Category",
            description = "Returns events associated with a specific category.")
    public ResponseEntity<List<Event>> getEventsByCategory(
            @Parameter(description = "Event categories") @RequestParam List<String> categories) {
        List<Category> categoryEnums = categories.stream().map(Category::valueOf).collect(Collectors.toList());
        List<Event> events = eventService.getFilteredEvents(categoryEnums);

        if (events.isEmpty()) return ResponseEntity.noContent().build();
        Collections.sort(events, Comparator.comparing(Event::getId));
        return ResponseEntity.ok(events);
    }

    @Operation(summary = "Create In-Person Event",
            description = "Create a new in-person event.")
    @PostMapping("/inPerson")
    public ResponseEntity<Event> createInPersonEvent(
            @Validated @RequestBody EventInPerson newEvent) {
        Event createdEvent = eventService.createInPersonEvent(newEvent);
        return ResponseEntity.ok().body(createdEvent);
    }

    @Operation(summary = "Create Online Event",
            description = "Create a new online event.")
    @PostMapping("/online")
    public ResponseEntity<Event> createOnlineEvent(
            @Validated @RequestBody EventOnline newEvent) {
        Event createdEvent = eventService.createOnlineEvent(newEvent);
        return ResponseEntity.ok().body(createdEvent);
    }

    @Operation(summary = "Edit In-Person Event", description = "Edit an existing in-person event.")
    @PatchMapping("/edit/inPerson/{id}")
    public ResponseEntity<Event> editInPersonEvent(
            @Parameter(description = "Event ID") @PathVariable Integer id,
            @Parameter(description = "Patched in-person event JSON") @Validated @RequestBody EventInPerson updatedEvent) {
        Event editedEvent = eventService.editInPersonEvent(id, updatedEvent);
        if (editedEvent != null) {
            return ResponseEntity.ok(editedEvent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Edit Online Event", description = "Edit an existing online event.")
    @PatchMapping("/edit/online/{id}")
    public ResponseEntity<Event> editOnlineEvent(
            @Parameter(description = "Event ID") @PathVariable Integer id,
            @Parameter(description = "Patched online event JSON") @Validated @RequestBody EventOnline updatedEvent) {
        Event editedEvent = eventService.editOnlineEvent(id, updatedEvent);
        if (editedEvent != null) {
            return ResponseEntity.ok(editedEvent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete Event by ID",
            description = "Delete an event by its unique ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventById(
            @Parameter(description = "Event ID") @PathVariable Integer id) {
        boolean deleted = eventService.deleteEventById(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
