package bubbles.springapibackend.service.bubble;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.bubble.dto.BubbleRequestDTO;
import bubbles.springapibackend.domain.bubble.dto.BubbleResponseDTO;
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

    public List<BubbleResponseDTO> getAllBubbles() {
        return bubbleRepository.findAll().stream()
                .map(bubbleMapper::toDTO).collect(Collectors.toList());
    }

    public Bubble getBubbleById(Integer bubbleId) {
        return bubbleRepository.findById(bubbleId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Bolha com ID: " + bubbleId + " não encontrado!"));
    }

    public List<BubbleResponseDTO> getAllBubblesByTitleContainsIgnoreCase(String bubbleTitle) {
        return bubbleRepository.findAllByTitleContainsIgnoreCase(bubbleTitle).stream()
                .map(bubbleMapper::toDTO).collect(Collectors.toList());
    }

    public List<BubbleResponseDTO> getAllBubblesByCreationDateAfter(LocalDate bubbleCreationDate) {
        return bubbleRepository.findAllByCreationDateIsGreaterThanEqual(bubbleCreationDate).stream()
                .map(bubbleMapper::toDTO).collect(Collectors.toList());
    }

    public List<BubbleResponseDTO> getAllBubblesByCreationDateBefore(LocalDate bubbleCreationDate) {
        return bubbleRepository.findAllByCreationDateIsLessThanEqual(bubbleCreationDate).stream()
                .map(bubbleMapper::toDTO).collect(Collectors.toList());
    }

    public List<BubbleResponseDTO> getAllBubblesByCategory(List<Category> bubbleCategories) {
        return bubbleRepository.findAllByCategory(bubbleCategories).stream()
                .map(bubbleMapper::toDTO).collect(Collectors.toList());
    }

    public List<BubbleResponseDTO> getAllBubblesByUserId(Integer userId){
        return bubbleRepository.findAllByCreatorIdUser(userId).stream()
                .map(bubbleMapper::toDTO).collect(Collectors.toList());
    }

    public List<BubbleResponseDTO> getAllBubblesByUserNickname(String userNickname) {
        return bubbleRepository.findAllByCreatorNickname(userNickname).stream()
                .map(bubbleMapper::toDTO).collect(Collectors.toList());
    }

    public BubbleResponseDTO createNewBubble(BubbleRequestDTO newBubbleDTO) {
        if (newBubbleDTO.getCreator() == null) {
            throw new IllegalArgumentException("fkUser não pode ser nula");
        }

        User user = userService.getUserById(newBubbleDTO.getCreator());

        Bubble newBubble = new Bubble();
        newBubble.setTitle(newBubbleDTO.getTitle());
        newBubble.setExplanation(newBubbleDTO.getExplanation());
        newBubble.setCreationDate(LocalDate.now());
        newBubble.setCategory(newBubbleDTO.getCategory());
        newBubble.setCreator(user);

        return bubbleMapper.toDTO(bubbleRepository.save(newBubble));
    }

    public BubbleResponseDTO updateBubbleById(Integer bubbleId, BubbleResponseDTO updatedBubbleDTO) {
        Bubble updatedBubble = bubbleRepository.findById(bubbleId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Bolha com ID: " + bubbleId + " não encontrado!"));

        updatedBubble.setTitle(updatedBubbleDTO.getTitle());
        updatedBubble.setExplanation(updatedBubbleDTO.getExplanation());

        return bubbleMapper.toDTO(bubbleRepository.save(updatedBubble));
    }

    public void deleteBubbleById(Integer bubbleId) {
        List<Event> events = eventRepository.findAllByBubbleIdBubble(bubbleId);
        for (Event event : events) {
            event.setBubble(null);
            eventRepository.save(event);
        }

        bubbleRepository.deleteById(bubbleId);
    }
}