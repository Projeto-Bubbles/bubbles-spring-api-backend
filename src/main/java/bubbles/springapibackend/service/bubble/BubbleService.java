package bubbles.springapibackend.service.bubble;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.bubble.dto.BubbleDTO;
import bubbles.springapibackend.domain.bubble.mapper.BubbleMapper;
import bubbles.springapibackend.domain.bubble.repository.BubbleRepository;
import bubbles.springapibackend.domain.event.Event;
import bubbles.springapibackend.domain.event.repository.EventRepository;
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
    private final EventRepository eventRepository;

    public List<BubbleDTO> getAllBubbles() {
        return bubbleRepository.findAll().stream()
                .map(bubbleMapper::toDTO).collect(Collectors.toList());
    }

    public Bubble getBubbleById(Integer bubbleId) {
        return bubbleRepository.findById(bubbleId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Bolha com ID: " + bubbleId + " não encontrado!"));
    }

    public List<BubbleDTO> getAllBubblesByHeadlineContainsIgnoreCase(String bubbleHeadline) {
        return bubbleRepository.findAllByHeadlineContainsIgnoreCase(bubbleHeadline).stream()
                .map(bubbleMapper::toDTO).collect(Collectors.toList());
    }

    public List<BubbleDTO> getAllBubblesByCategory(List<Category> bubbleCategories) {
        return bubbleRepository.findAllByCategory(bubbleCategories).stream()
                .map(bubbleMapper::toDTO).collect(Collectors.toList());
    }

    public List<BubbleDTO> getAllBubblesByCreatorId(Integer creatorId){
        return bubbleRepository.findAllByCreatorId(creatorId).stream()
                .map(bubbleMapper::toDTO).collect(Collectors.toList());
    }

    public List<BubbleDTO> getAllBubblesByCreatorNickname(String creatorNickname) {
        return bubbleRepository.findAllByCreatorNickname(creatorNickname).stream()
                .map(bubbleMapper::toDTO).collect(Collectors.toList());
    }

    public BubbleDTO createNewBubble(BubbleDTO newBubbleDTO) {
        User user = userService.getUserByNickname(newBubbleDTO.getCreator());

        Bubble newBubble = new Bubble();
        newBubble.setId(newBubbleDTO.getId());
        newBubble.setHeadline(newBubbleDTO.getHeadline());
        newBubble.setExplanation(newBubbleDTO.getExplanation());
        newBubble.setCreationDate(newBubbleDTO.getCreationDate());
        newBubble.setCategory(newBubbleDTO.getCategory());
        newBubble.setCreator(user);

        return bubbleMapper.toDTO(bubbleRepository.save(newBubble));
    }

    public BubbleDTO updateBubbleById(Integer bubbleId, BubbleDTO updatedBubbleDTO) {
        Bubble updatedBubble = bubbleRepository.findById(bubbleId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Bolha com ID: " + bubbleId + " não encontrado!"));

        updatedBubble.setHeadline(updatedBubbleDTO.getHeadline());
        updatedBubble.setExplanation(updatedBubbleDTO.getExplanation());

        return bubbleMapper.toDTO(bubbleRepository.save(updatedBubble));
    }

    public void deleteBubbleById(Integer bubbleId) {
        List<Event> events = eventRepository.findAllByBubbleId(bubbleId);
        for (Event event : events) {
            event.setBubble(null);
            eventRepository.save(event);
        }

        bubbleRepository.deleteById(bubbleId);
    }
}