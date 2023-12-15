package bubbles.springapibackend.service.bubble;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.bubble.dto.BubbleDTO;
import bubbles.springapibackend.domain.bubble.mapper.BubbleMapper;
import bubbles.springapibackend.domain.bubble.repository.BubbleRepository;
import bubbles.springapibackend.domain.user.User;
import bubbles.springapibackend.service.user.UserService;
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
    private final UserService userService;

    public List<BubbleDTO> getAllBubbles() {
        return bubbleRepository.findAll().stream()
                .map(bubbleMapper::toDTO).collect(Collectors.toList());
    }

    public Bubble getBubbleById(Integer bubbleId) {
        return bubbleRepository.findById(bubbleId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Bolha com ID: " + bubbleId + " não encontrado!"));
    }

    public List<BubbleDTO> getBubbleByCreatorNickname(String creatorNickname) {
        return bubbleRepository.findAllByCreatorNickname(creatorNickname).stream()
                .map(bubbleMapper::toDTO).collect(Collectors.toList());
    }

    public List<BubbleDTO> getFilteredBubbles(List<Category> categories) {
        return bubbleRepository.findAllByCategory(categories).stream()
                .map(bubbleMapper::toDTO).collect(Collectors.toList());
    }

    public BubbleDTO createBubble(BubbleDTO newBubbleDTO) {
        String userNickname = newBubbleDTO.getCreator();
        User user = userService.getUserByNickname(userNickname);

        Bubble newBubble = new Bubble();
        newBubble.setId(newBubbleDTO.getId());
        newBubble.setHeadline(newBubbleDTO.getHeadline());
        newBubble.setExplanation(newBubbleDTO.getExplanation());
        newBubble.setCreationDate(newBubbleDTO.getCreationDate());
        newBubble.setCategory(newBubbleDTO.getCategory());
        newBubble.setCreator(user);

        Bubble savedBubble = bubbleRepository.save(newBubble);
        return bubbleMapper.toDTO(savedBubble);
    }

    public BubbleDTO updateBubble(Integer bubbleId, BubbleDTO updatedBubbleDTO) {
        Bubble existingBubble = bubbleRepository.findById(bubbleId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Bolha com ID: " + bubbleId + " não encontrado!"));

        existingBubble.setHeadline(updatedBubbleDTO.getHeadline());
        existingBubble.setExplanation(updatedBubbleDTO.getExplanation());

        return bubbleMapper.toDTO(bubbleRepository.save(existingBubble));
    }

    public void deleteBubbleById(Integer id) {
        bubbleRepository.deleteById(id);
    }
}