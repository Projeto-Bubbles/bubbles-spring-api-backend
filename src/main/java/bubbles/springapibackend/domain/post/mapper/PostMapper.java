package bubbles.springapibackend.domain.post.mapper;

import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.comment.mapper.CommentMapper;
import bubbles.springapibackend.domain.post.Post;
import bubbles.springapibackend.domain.post.dto.PostRequestDTO;
import bubbles.springapibackend.domain.post.dto.PostResponseDTO;
import bubbles.springapibackend.domain.user.User;
import bubbles.springapibackend.service.bubble.BubbleService;
import bubbles.springapibackend.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class PostMapper {

    private CommentMapper commentMapper;
    private UserService userService;
    private BubbleService bubbleService;

    public PostResponseDTO toDTO(Post post) {
        PostResponseDTO dto = new PostResponseDTO();
        dto.setId(post.getId());
        dto.setDateTime(post.getMoment());
        dto.setContent(post.getContent());
        dto.setAuthor(post.getAuthor());
        dto.setBubble(post.getBubble());
        if (post.getComments() != null) {
            dto.setComments(post.getComments().stream()
                    .map(commentMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public Post toEntity(PostRequestDTO dto) {
        Post post = new Post();
        post.setContent(dto.getContent());
        User author = userService.getUserById(dto.getAuthorId());
        Bubble bubble = bubbleService.getBubbleById(dto.getBubbleId());
        post.setAuthor(author);
        post.setBubble(bubble);
        return post;
    }
}
