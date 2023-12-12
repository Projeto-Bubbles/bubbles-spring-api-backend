package bubbles.springapibackend.service.event;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.event.Event;
import bubbles.springapibackend.domain.event.EventInPerson;
import bubbles.springapibackend.domain.event.EventOnline;
import bubbles.springapibackend.domain.event.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public List<Event> getAvailableEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Integer id) {
        return eventRepository.findById(id);
    }

    public List<Event> getEventsByAuthor(String author) {
        return eventRepository.findByAuthorUsername(author);
    }

    public List<Event> getEventsByBubble(String bubble) {
        return eventRepository.findByBubbleHeadline(bubble);
    }

    public List<Event> getFilteredEvents(List<Category> categories) {
        return eventRepository.findFilteredEvents(categories);
    }

    public Event createInPersonEvent(EventInPerson newEvent) {
        return eventRepository.save(newEvent);
    }

    public Event createOnlineEvent(EventOnline newEvent) {
        return eventRepository.save(newEvent);
    }

    public Event editInPersonEvent(Integer id, EventInPerson updatedEvent) {
        Optional<Event> existingEventOpt = eventRepository.findById(id);
        if (existingEventOpt.isPresent()) {
            EventInPerson existingEvent = (EventInPerson) existingEventOpt.get();
            existingEvent.setTitle(updatedEvent.getTitle());
            return eventRepository.save(existingEvent);
        }
        return null;
    }

    public Event editOnlineEvent(Integer id, EventOnline updatedEvent) {
        Optional<Event> existingEventOpt = eventRepository.findById(id);
        if (existingEventOpt.isPresent()) {
            EventOnline existingEvent = (EventOnline) existingEventOpt.get();
            existingEvent.setTitle(updatedEvent.getTitle());
            return eventRepository.save(existingEvent);
        }
        return null;
    }

    public boolean deleteEventById(Integer id) {
        Optional<Event> existingEventOpt = eventRepository.findById(id);
        if (existingEventOpt.isPresent()) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
