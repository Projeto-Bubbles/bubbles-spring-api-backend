package bubbles.springapibackend.domain.post.mapper;

import bubbles.springapibackend.domain.bubble.mapper.BubbleMapper;
import bubbles.springapibackend.domain.comment.mapper.CommentMapper;
import bubbles.springapibackend.domain.post.Post;
import bubbles.springapibackend.domain.post.dto.PostResponseDTO;
import bubbles.springapibackend.domain.user.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PostMapper {
    private CommentMapper commentMapper;
    private UserMapper userMapper;
    private BubbleMapper bubbleMapper;

    public PostResponseDTO toDTO(Post post) {
        if (post == null) {
            throw new EntityNotFoundException("Post com valor nulo");
        }

        PostResponseDTO dto = new PostResponseDTO();
        dto.setIdPost(post.getIdPost());
        dto.setMoment(post.getMoment());
        dto.setImage(post.getImage());
        dto.setContents(post.getContents());
        dto.setAuthor(userMapper.toUserInfoDTO(post.getAuthor()));
        dto.setBubble(bubbleMapper.toBubbleInfoDTO(post.getBubble()));
        if (post.getComments() != null) {
            dto.setComments(post.getComments().stream().map(commentMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
