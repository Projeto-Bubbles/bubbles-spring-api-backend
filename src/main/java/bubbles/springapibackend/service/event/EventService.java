package bubbles.springapibackend.service.event;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.event.Event;
import bubbles.springapibackend.domain.event.EventInPerson;
import bubbles.springapibackend.domain.event.EventOnline;
import bubbles.springapibackend.domain.event.dto.EventDTO;
import bubbles.springapibackend.domain.event.dto.EventInPersonDTO;
import bubbles.springapibackend.domain.event.dto.EventOnlineDTO;
import bubbles.springapibackend.domain.event.mapper.EventMapper;
import bubbles.springapibackend.domain.event.repository.EventRepository;
import bubbles.springapibackend.domain.user.User;
import bubbles.springapibackend.service.bubble.BubbleService;
import bubbles.springapibackend.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserService userService;
    private final BubbleService bubbleService;

    public List<EventDTO> getAvailableEvents() {
        return eventRepository.findAll().stream()
                .map(eventMapper::toDTO).collect(Collectors.toList());
    }

    public Event getEventById(Integer eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Bolha com ID: " + eventId + " não encontrado!"));
    }

    public List<EventDTO> getEventsByAuthor(String author) {
        return eventRepository.findAllByCreatorUsername(author).stream()
                .map(eventMapper::toDTO).collect(Collectors.toList());
    }

    public List<EventDTO> getEventsByBubble(String bubble) {
        return eventRepository.findByBubbleTitle(bubble).stream()
                .map(eventMapper::toDTO).collect(Collectors.toList());
    }

    public List<EventDTO> getFilteredEvents(List<Category> categories) {
        return eventRepository.findAllByBubbleCategory(categories).stream()
                .map(eventMapper::toDTO).collect(Collectors.toList());
    }

    public EventDTO createInPersonEvent(EventInPersonDTO newEventInPersonDTO) {
        User user = userService.getUserByUsername(newEventInPersonDTO.getCreator());
        Bubble bubble = bubbleService.getBubbleById(newEventInPersonDTO.getBubbleId());

        EventInPerson newEventInPerson = new EventInPerson(
                newEventInPersonDTO.getId(),
                newEventInPersonDTO.getTitle(),
                newEventInPersonDTO.getDateTime(),
                newEventInPersonDTO.getDuration(),
                user,
                bubble,
                newEventInPersonDTO.isPublicPlace(),
                newEventInPersonDTO.getPeopleCapacity(),
                newEventInPersonDTO.getAddress()
        );

        Event savedEventInPerson = eventRepository.save(newEventInPerson);
        return eventMapper.toDTO(savedEventInPerson);
    }

    public EventDTO createOnlineEvent(EventOnlineDTO newEventOnlineDTO) {
        User user = userService.getUserByUsername(newEventOnlineDTO.getCreator());
        Bubble bubble = bubbleService.getBubbleById(newEventOnlineDTO.getBubbleId());

        EventOnline newEventOnline = new EventOnline(
                newEventOnlineDTO.getId(),
                newEventOnlineDTO.getTitle(),
                newEventOnlineDTO.getDateTime(),
                newEventOnlineDTO.getDuration(),
                user,
                bubble,
                newEventOnlineDTO.getPlatform(),
                newEventOnlineDTO.getUrl()
        );

        Event savedEventOnline = eventRepository.save(newEventOnline);
        return eventMapper.toDTO(savedEventOnline);
    }

    public EventInPersonDTO editInPersonEvent(Integer eventId, EventInPersonDTO updatedEventInPersonDTO) {
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Evento com ID: " + eventId + " não encontrado!"));

        existingEvent.setTitle(updatedEventInPersonDTO.getTitle());
        existingEvent.setDateTime(updatedEventInPersonDTO.getDateTime());
        existingEvent.setDuration(updatedEventInPersonDTO.getDuration());

        EventInPerson eventInPerson = (EventInPerson) existingEvent;
        eventInPerson.setPublicPlace(updatedEventInPersonDTO.isPublicPlace());
        eventInPerson.setPeopleCapacity(updatedEventInPersonDTO.getPeopleCapacity());
        eventInPerson.setAddress(updatedEventInPersonDTO.getAddress());

        return (EventInPersonDTO) eventMapper.toDTO(eventRepository.save(eventInPerson));
    }

    public EventOnlineDTO editOnlineEvent(Integer eventId, EventOnlineDTO updatedEventOnlineDTO) {
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Evento com ID: " + eventId + " não encontrado!"));

        existingEvent.setTitle(updatedEventOnlineDTO.getTitle());
        existingEvent.setDateTime(updatedEventOnlineDTO.getDateTime());
        existingEvent.setDuration(updatedEventOnlineDTO.getDuration());

        EventOnline eventOnline = (EventOnline) existingEvent;
        eventOnline.setPlatform(updatedEventOnlineDTO.getPlatform());
        eventOnline.setUrl(updatedEventOnlineDTO.getUrl());

        return (EventOnlineDTO) eventMapper.toDTO(eventRepository.save(eventOnline));
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
