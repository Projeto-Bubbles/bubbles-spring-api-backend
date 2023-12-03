package bubbles.springapibackend.service.bubble;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.bubble.repository.BubbleRepository;
import bubbles.springapibackend.domain.event.Event;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BubbleService {
    private final BubbleRepository bubbleRepository;

    public List<Bubble> getFilteredBubbles(List<Category> categories) {
        return bubbleRepository.findAllByCategory(categories);
    }
}