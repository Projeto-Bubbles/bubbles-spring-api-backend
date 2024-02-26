package bubbles.springapibackend.domain.bubble.mapper;

import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.bubble.dto.BubbleResponseDTO;
import bubbles.springapibackend.domain.user.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BubbleMapper {
    private final UserMapper userMapper;

    @Autowired
    public BubbleMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public BubbleResponseDTO toDTO(Bubble bubble) {
        if (bubble == null || bubble.getCreator() == null) {
            throw new EntityNotFoundException("Bolha nula ou criador nulo");
        }

        BubbleResponseDTO bubbleDTO = new BubbleResponseDTO();
        bubbleDTO.setIdBubble(bubble.getIdBubble());
        bubbleDTO.setTitle(bubble.getTitle());
        bubbleDTO.setExplanation(bubble.getExplanation());
        bubbleDTO.setCreationDate(bubble.getCreationDate());
        bubbleDTO.setCategory(bubble.getCategory());
        bubbleDTO.setCreator(userMapper.toUserBubbleDTO(bubble.getCreator()));

        return bubbleDTO;
    }
}
