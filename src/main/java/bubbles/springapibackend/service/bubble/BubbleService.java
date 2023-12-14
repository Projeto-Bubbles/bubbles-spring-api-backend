package bubbles.springapibackend.service.bubble;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.bubble.dto.BubbleDTO;
import bubbles.springapibackend.domain.bubble.mapper.BubbleMapper;
import bubbles.springapibackend.domain.bubble.repository.BubbleRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BubbleService {
    private final BubbleRepository bubbleRepository;
    private final BubbleMapper bubbleMapper;

    public List<BubbleDTO> getAllBubbles() {
        return bubbleRepository.findAll().stream()
                .map(bubbleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Bubble getBubbleById(Integer bubbleId) {
        return bubbleRepository.findById(bubbleId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Bolha com ID: " + bubbleId + " não encontrado!"));
    }

    public List<BubbleDTO> getFilteredBubbles(List<Category> categories) {
        return bubbleRepository.findAllByCategory(categories).stream()
                .map(bubbleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<Bubble> getBubbleByCreatorNickname(String nickname) {
        return bubbleRepository.findAllByCreatorNickname(nickname);
    }

    public Bubble createBubble(Bubble bubble) {
        return bubbleRepository.save(bubble);
    }

    public Bubble updateBubble(Bubble bubble) {
        return bubbleRepository.save(bubble);
    }

    public void deleteBubbleById(Integer id) {
        bubbleRepository.deleteById(id);
    }
}