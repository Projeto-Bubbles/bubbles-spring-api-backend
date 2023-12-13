package bubbles.springapibackend.service.bubble;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.bubble.repository.BubbleRepository;
import bubbles.springapibackend.domain.event.Event;
import bubbles.springapibackend.domain.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BubbleService {
    private final BubbleRepository bubbleRepository;

    public Bubble getBubbleById(Integer bubbleId) {
        return bubbleRepository.findById(bubbleId).orElseThrow(() -> new EntityNotFoundException(
                "Bubble with ID " + bubbleId + " not found"));
    }

    public List<Bubble> getFilteredBubbles(List<Category> categories) {
        return bubbleRepository.findAllByCategory(categories);
    }
}