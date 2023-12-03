package bubbles.springapibackend.service.event;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.event.Event;
import bubbles.springapibackend.domain.event.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
        private final EventRepository eventRepository;

        public List<Event> getFilteredEvents(List<Category> categories) {
            return eventRepository.findFilteredEvents(categories);
        }
}
