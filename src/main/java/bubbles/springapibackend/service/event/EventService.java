package bubbles.springapibackend.service.event;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.event.Event;
import bubbles.springapibackend.domain.event.EventInPerson;
import bubbles.springapibackend.domain.event.EventOnline;
import bubbles.springapibackend.domain.event.dto.*;
import bubbles.springapibackend.domain.event.mapper.EventMapper;
import bubbles.springapibackend.domain.event.repository.EventRepository;
import bubbles.springapibackend.domain.user.User;
import bubbles.springapibackend.service.bubble.BubbleService;
import bubbles.springapibackend.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

    public List<EventResponseDTO> getAvailableEvents() {
        return eventRepository.findAll().stream()
                .map(eventMapper::toDTO).collect(Collectors.toList());
    }

    public Event getEventById(Integer eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Bolha com ID: " + eventId + " não encontrado!"));
    }

    public List<EventResponseDTO> getEventsByUserNickname(String userNickname) {
        return eventRepository.findAllByOrganizerNickname(userNickname).stream()
                .map(eventMapper::toDTO).collect(Collectors.toList());
    }

    public List<EventResponseDTO> getEventsByBubbleTitle(String bubbleTitle) {
        return eventRepository.findByBubbleTitle(bubbleTitle).stream()
                .map(eventMapper::toDTO).collect(Collectors.toList());
    }

    public List<EventResponseDTO> getFilteredEvents(List<Category> categories) {
        return eventRepository.findAllByBubbleCategory(categories).stream()
                .map(eventMapper::toDTO).collect(Collectors.toList());
    }

    public EventResponseDTO createInPersonEvent(EventInPersonRequestDTO newEventInPersonDTO) {
        if (newEventInPersonDTO.getIdCreator() == null) {
            throw new IllegalArgumentException("fkUser não pode ser nula");
        }
        User user = userService.getUserById(newEventInPersonDTO.getIdCreator());
        Bubble bubble = bubbleService.getBubbleById(newEventInPersonDTO.getIdBubble());

        EventInPerson newEventInPerson = new EventInPerson();
        newEventInPerson.setTitle(newEventInPersonDTO.getTitle());
        newEventInPerson.setMoment(newEventInPersonDTO.getDateTime());
        newEventInPerson.setDuration(newEventInPersonDTO.getDuration());
        newEventInPerson.setOrganizer(user);
        newEventInPerson.setBubble(bubble);
        newEventInPerson.setPublicPlace(newEventInPersonDTO.isPublicPlace());
        newEventInPerson.setPeopleCapacity(newEventInPersonDTO.getPeopleCapacity());
        newEventInPerson.setAddress(newEventInPersonDTO.getAddress());

        return eventMapper.toDTO(eventRepository.save(newEventInPerson));
    }

    public EventResponseDTO createOnlineEvent(EventOnlineRequestDTO newEventOnlineDTO) {
        if (newEventOnlineDTO.getIdCreator() == null) {
            throw new IllegalArgumentException("fkUser não pode ser nula");
        }
        User user = userService.getUserById(newEventOnlineDTO.getIdCreator());
        Bubble bubble = bubbleService.getBubbleById(newEventOnlineDTO.getIdBubble());

        EventOnline newEventOnline = new EventOnline();
        newEventOnline.setTitle(newEventOnlineDTO.getTitle());
        newEventOnline.setMoment(newEventOnlineDTO.getDateTime());
        newEventOnline.setDuration(newEventOnlineDTO.getDuration());
        newEventOnline.setOrganizer(user);
        newEventOnline.setBubble(bubble);
        newEventOnline.setPlatform(newEventOnlineDTO.getPlatform());
        newEventOnline.setLink(newEventOnlineDTO.getLink());

        return eventMapper.toDTO(eventRepository.save(newEventOnline));
    }

    public EventInPersonResponseDTO editInPersonEvent(Integer eventId, EventInPersonResponseDTO updatedEventInPersonDTO) {
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Evento com ID: " + eventId + " não encontrado!"));

        existingEvent.setTitle(updatedEventInPersonDTO.getTitle());
        existingEvent.setMoment(updatedEventInPersonDTO.getMoment());
        existingEvent.setDuration(updatedEventInPersonDTO.getDuration());

        EventInPerson eventInPerson = (EventInPerson) existingEvent;
        eventInPerson.setPublicPlace(updatedEventInPersonDTO.isPublicPlace());
        eventInPerson.setPeopleCapacity(updatedEventInPersonDTO.getPeopleCapacity());
        eventInPerson.setAddress(updatedEventInPersonDTO.getAddress());

        return (EventInPersonResponseDTO) eventMapper.toDTO(eventRepository.save(eventInPerson));
    }

    public EventOnlineResponseDTO editOnlineEvent(Integer eventId, EventOnlineResponseDTO updatedEventOnlineDTO) {
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Evento com ID: " + eventId + " não encontrado!"));

        existingEvent.setTitle(updatedEventOnlineDTO.getTitle());
        existingEvent.setMoment(updatedEventOnlineDTO.getMoment());
        existingEvent.setDuration(updatedEventOnlineDTO.getDuration());

        EventOnline eventOnline = (EventOnline) existingEvent;
        eventOnline.setPlatform(updatedEventOnlineDTO.getPlatform());
        eventOnline.setLink(updatedEventOnlineDTO.getLink());

        return (EventOnlineResponseDTO) eventMapper.toDTO(eventRepository.save(eventOnline));
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
