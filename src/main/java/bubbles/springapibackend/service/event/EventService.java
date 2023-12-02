package bubbles.springapibackend.service.event;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.event.Event;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EventService {
    List<Event> getFilteredEvents(List<String> categories);
}
