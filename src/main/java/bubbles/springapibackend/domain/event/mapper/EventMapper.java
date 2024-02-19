package bubbles.springapibackend.domain.event.mapper;

import bubbles.springapibackend.domain.event.Event;
import bubbles.springapibackend.domain.event.EventInPerson;
import bubbles.springapibackend.domain.event.EventOnline;
import bubbles.springapibackend.domain.event.dto.EventDTO;
import bubbles.springapibackend.domain.event.dto.EventInPersonDTO;
import bubbles.springapibackend.domain.event.dto.EventOnlineDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public EventDTO toDTO(Event event) {
        validateEvent(event);

        EventDTO eventDTO;
        if (event instanceof EventInPerson) {
            eventDTO = eventInPersonToDTO((EventInPerson) event);
        } else if (event instanceof EventOnline) {
            eventDTO = eventOnlineToDTO((EventOnline) event);
        } else {
            throw new IllegalArgumentException("Tipo de evento inválido: " + event.getClass());
        }

        return eventDTO;
    }

    private void validateEvent(Event event) {
        if (event == null) {
            throw new EntityNotFoundException("Evento nulo!");
        }
    }

    private void mapAttributes(Event event, EventDTO eventDTO) {
        eventDTO.setId(event.getId());
        eventDTO.setTitle(event.getTitle());
        eventDTO.setDateTime(event.getDateTime());
        eventDTO.setDuration(event.getDuration());
        eventDTO.setCreator(event.getCreator().getUsername());
        eventDTO.setBubbleId(event.getBubble().getId());
    }

    private EventInPersonDTO eventInPersonToDTO(EventInPerson eventInPerson) {
        EventInPersonDTO eventDTO = new EventInPersonDTO();
        mapAttributes(eventInPerson, eventDTO);

        eventDTO.setPublicPlace(eventInPerson.isPublicPlace());
        eventDTO.setPeopleCapacity(eventInPerson.getPeopleCapacity());
        if (eventInPerson.getAddress() != null) {
            eventDTO.setAddress(eventInPerson.getAddress());
        }

        return eventDTO;
    }

    private EventOnlineDTO eventOnlineToDTO(EventOnline eventOnline) {
        EventOnlineDTO eventDTO = new EventOnlineDTO();
        mapAttributes(eventOnline, eventDTO);

        eventDTO.setPlatform(eventOnline.getPlatform());
        eventDTO.setUrl(eventOnline.getUrl());

        return eventDTO;
    }
}
