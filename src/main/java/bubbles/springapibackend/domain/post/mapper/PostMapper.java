package bubbles.springapibackend.domain.post.mapper;

import bubbles.springapibackend.domain.comment.mapper.CommentMapper;
import bubbles.springapibackend.domain.post.Post;
import bubbles.springapibackend.domain.post.dto.PostResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PostMapper {
    private CommentMapper commentMapper;

    public PostResponseDTO toDTO(Post post) {
        if (post == null) {
            throw new EntityNotFoundException("Post com valor nulo");
        }

        PostResponseDTO dto = new PostResponseDTO();
        dto.setId(post.getIdPost());
        dto.setDate_time(post.getMoment());
        dto.setContent(post.getContents());
        dto.setAuthor(post.getFkUser());
        dto.setBubble(post.getFkBubble().getTitle());
        if (post.getComments() != null) {
            dto.setComments(post.getComments().stream().map(commentMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
