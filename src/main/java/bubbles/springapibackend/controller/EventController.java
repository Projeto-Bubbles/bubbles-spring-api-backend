package bubbles.springapibackend.controller;
import bubbles.springapibackend.entity.Event;
import bubbles.springapibackend.entity.EventInPerson;
import bubbles.springapibackend.entity.EventOnline;
import bubbles.springapibackend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventRepository eventRepository;
    @GetMapping
    public ResponseEntity<List<Event>> getEvents() {
        List<Event> events = this.eventRepository.findAll();
        if(events.isEmpty())return ResponseEntity.noContent().build();
        return ResponseEntity.ok(events);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Event>> getById(@PathVariable Integer id) {
        Optional<Event> eventOpt = this.eventRepository.findById(id);
        return ResponseEntity.ok().body(eventOpt);
    }
    @GetMapping("/author")
    public ResponseEntity<List<Event>> getByAuthor(@RequestParam String author) {
        List<Event> events = this.eventRepository.findByAuthor_Name(author);
        if(events.isEmpty())return  ResponseEntity.noContent().build();
        return ResponseEntity.ok(events);
    }
    @GetMapping("/bubble")
    public ResponseEntity<List<Event>> getByBubble(@RequestParam String bubble) {
        List<Event> events = this.eventRepository.findByBubble_Name(bubble);
        if(events.isEmpty())return  ResponseEntity.noContent().build();
        return ResponseEntity.ok(events);
    }
    @PostMapping("/inPerson")
    public ResponseEntity<Event> createPost(@Validated @RequestBody EventInPerson newEvent) {
        EventInPerson savedEvent = eventRepository.save(newEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }
    @PostMapping("/online")
    public ResponseEntity<Event> createPost(@Validated @RequestBody EventOnline newEvent) {
        EventOnline savedEvent = eventRepository.save(newEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }
    @PutMapping("/edit/inPerson/{id}")
    public ResponseEntity<Event> editPostInPerson(@PathVariable Integer id,@Validated @RequestBody EventInPerson updatedEvent) {
        Optional<Event> existingEventOpt = eventRepository.findById(id);
        if (existingEventOpt.isPresent()) {
            EventInPerson existingEvent = (EventInPerson) existingEventOpt.get();
            existingEvent.setTitle(updatedEvent.getTitle());
            existingEvent.setDateTime(updatedEvent.getDateTime());
            existingEvent.setCategory(updatedEvent.getCategory());
            existingEvent.setDuration(updatedEvent.getDuration());
            updatedEvent = eventRepository.save(existingEvent);
            return ResponseEntity.ok(updatedEvent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }@PutMapping("/edit/online/{id}")
    public ResponseEntity<Event> editPostOnline(@PathVariable Integer id,@Validated @RequestBody EventOnline updatedEvent) {
        Optional<Event> existingEventOpt = eventRepository.findById(id);
        if (existingEventOpt.isPresent()) {
            EventOnline existingEvent = (EventOnline) existingEventOpt.get();
            existingEvent.setTitle(updatedEvent.getTitle());
            existingEvent.setDateTime(updatedEvent.getDateTime());
            existingEvent.setCategory(updatedEvent.getCategory());
            existingEvent.setDuration(updatedEvent.getDuration());
            updatedEvent = eventRepository.save(existingEvent);
            return ResponseEntity.ok(updatedEvent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        Optional<Event> existingEventOpt = eventRepository.findById(id);

        if (existingEventOpt.isPresent()) {
            eventRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
