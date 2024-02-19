package bubbles.springapibackend.domain.bubble.mapper;

import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.bubble.dto.BubbleDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class BubbleMapper {
    public BubbleDTO toDTO(Bubble bubble) {
        if (bubble == null || bubble.getCreator() == null) {
            throw new EntityNotFoundException("Bolha nula ou criador nulo");
        }

        BubbleDTO bubbleDTO = new BubbleDTO();
        bubbleDTO.setId(bubble.getId());
        bubbleDTO.setTitle(bubble.getTitle());
        bubbleDTO.setDescription(bubble.getDescription());
        bubbleDTO.setCreationDate(bubble.getCreationDate());
        bubbleDTO.setCategory(bubble.getCategory());
        bubbleDTO.setCreator(bubble.getCreator().getUsername());

        return bubbleDTO;
    }
}
