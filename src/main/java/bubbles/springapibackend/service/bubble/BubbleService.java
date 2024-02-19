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

import java.time.LocalDate;
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

    public List<BubbleDTO> getAllBubblesByTitleContainsIgnoreCase(String bubbleTitle) {
        return bubbleRepository.findAllByTitleContainsIgnoreCase(bubbleTitle).stream()
                .map(bubbleMapper::toDTO).collect(Collectors.toList());
    }

    public List<BubbleDTO> getAllBubblesByCreationDateAfter(LocalDate bubbleCreationDate) {
        return bubbleRepository.findAllByCreationDateIsGreaterThanEqual(bubbleCreationDate).stream()
                .map(bubbleMapper::toDTO).collect(Collectors.toList());
    }

    public List<BubbleDTO> getAllBubblesByCreationDateBefore(LocalDate bubbleCreationDate) {
        return bubbleRepository.findAllByCreationDateIsLessThanEqual(bubbleCreationDate).stream()
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

    public List<BubbleDTO> getAllBubblesByCreatorUsername(String creatorUsername) {
        return bubbleRepository.findAllByCreatorUsername(creatorUsername).stream()
                .map(bubbleMapper::toDTO).collect(Collectors.toList());
    }

    public BubbleDTO createNewBubble(BubbleDTO newBubbleDTO) {
        User user = userService.getUserByUsername(newBubbleDTO.getCreator());

        Bubble newBubble = new Bubble();
        newBubble.setId(newBubbleDTO.getId());
        newBubble.setTitle(newBubbleDTO.getTitle());
        newBubble.setDescription(newBubbleDTO.getDescription());
        newBubble.setCreationDate(newBubbleDTO.getCreationDate());
        newBubble.setCategory(newBubbleDTO.getCategory());
        newBubble.setCreator(user);

        return bubbleMapper.toDTO(bubbleRepository.save(newBubble));
    }

    public BubbleDTO updateBubbleById(Integer bubbleId, BubbleDTO updatedBubbleDTO) {
        Bubble updatedBubble = bubbleRepository.findById(bubbleId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Bolha com ID: " + bubbleId + " não encontrado!"));

        updatedBubble.setTitle(updatedBubbleDTO.getTitle());
        updatedBubble.setDescription(updatedBubbleDTO.getDescription());

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