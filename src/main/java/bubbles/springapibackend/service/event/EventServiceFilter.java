package bubbles.springapibackend.service.event;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.event.Event;
import bubbles.springapibackend.domain.event.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceFilter implements EventService{
        private final EventRepository eventRepository;

        @Override
        public List<Event> getFilteredEvents(List<String> categories) {
            return eventRepository.findFilteredEvents(categories);
        }
}
