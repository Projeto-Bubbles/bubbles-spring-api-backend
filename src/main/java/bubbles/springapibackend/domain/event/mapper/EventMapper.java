package bubbles.springapibackend.domain.event.mapper;

import bubbles.springapibackend.domain.event.Event;
import bubbles.springapibackend.domain.event.EventInPerson;
import bubbles.springapibackend.domain.event.EventOnline;
import bubbles.springapibackend.domain.event.dto.EventResponseDTO;
import bubbles.springapibackend.domain.event.dto.EventInPersonResponseDTO;
import bubbles.springapibackend.domain.event.dto.EventOnlineResponseDTO;
import bubbles.springapibackend.domain.event.dto.EventSimpleDTO;
import bubbles.springapibackend.domain.user.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    private final UserMapper userMapper;

    @Autowired
    public EventMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public EventResponseDTO toDTO(Event event) {
        if (event == null) {
            throw new EntityNotFoundException("Evento nulo!");
        }

        EventResponseDTO eventDTO;
        if (event instanceof EventInPerson) {
            eventDTO = eventInPersonToDTO((EventInPerson) event);
        } else if (event instanceof EventOnline) {
            eventDTO = eventOnlineToDTO((EventOnline) event);
        } else {
            throw new IllegalArgumentException("Tipo de evento inválido: " + event.getClass());
        }

        return eventDTO;
    }

    private void mapAttributes(Event event, EventResponseDTO eventDTO) {
        eventDTO.setIdEvent(event.getIdEvent());
        eventDTO.setTitle(event.getTitle());
        eventDTO.setDateTime(event.getMoment());
        eventDTO.setDuration(event.getDuration());
        eventDTO.setOrganizer(userMapper.toUserInfoDTO(event.getOrganizer()));
        eventDTO.setIdBubble(event.getBubble().getIdBubble());
    }

    private EventInPersonResponseDTO eventInPersonToDTO(EventInPerson eventInPerson) {
        EventInPersonResponseDTO eventInPersonDTO = new EventInPersonResponseDTO();
        mapAttributes(eventInPerson, eventInPersonDTO);

        eventInPersonDTO.setPublicPlace(eventInPerson.isPublicPlace());
        eventInPersonDTO.setPeopleCapacity(eventInPerson.getPeopleCapacity());
        if (eventInPerson.getAddress() != null) {
            eventInPersonDTO.setAddress(eventInPerson.getAddress());
        }

        return eventInPersonDTO;
    }

    private EventOnlineResponseDTO eventOnlineToDTO(EventOnline eventOnline) {
        EventOnlineResponseDTO eventOnlineDTO = new EventOnlineResponseDTO();
        mapAttributes(eventOnline, eventOnlineDTO);

        eventOnlineDTO.setPlatform(eventOnline.getPlatform());
        eventOnlineDTO.setLink(eventOnline.getLink());

        return eventOnlineDTO;
    }

    public EventSimpleDTO toEventSimpleDTO(Event event) {
        if (event == null || event.getBubble() == null || event.getOrganizer() == null) {
            throw new EntityNotFoundException("Evento, bolha ou organizador null");
        }

        EventSimpleDTO eventSimpleDTO = new EventSimpleDTO();
        eventSimpleDTO.setMoment(event.getMoment());
        eventSimpleDTO.setTitle(event.getTitle());
        eventSimpleDTO.setBubbleName(event.getBubble().getTitle());

        return eventSimpleDTO;
    }
}
