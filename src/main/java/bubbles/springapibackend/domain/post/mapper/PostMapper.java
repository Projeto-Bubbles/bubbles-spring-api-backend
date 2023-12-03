package bubbles.springapibackend.domain.post.mapper;


import bubbles.springapibackend.domain.comment.mapper.CommentMapper;
import bubbles.springapibackend.domain.post.Post;
import bubbles.springapibackend.domain.post.dto.PostRequestDTO;
import bubbles.springapibackend.domain.post.dto.PostResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component

@AllArgsConstructor
public class PostMapper {

    private CommentMapper commentMapper;

    public PostResponseDTO toDTO(Post post) {
        PostResponseDTO dto = new PostResponseDTO();
        dto.setId(post.getId());
        dto.setDateTime(post.getDateTime());
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

    public static Post toEntity(PostRequestDTO dto) {
        Post post = new Post();
        post.setContent(dto.getContent());
        post.setAuthor(dto.getAuthor());
        post.setBubble(dto.getBubble());
        return post;
    }
}