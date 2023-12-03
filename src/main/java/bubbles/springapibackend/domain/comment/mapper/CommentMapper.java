package bubbles.springapibackend.domain.comment.mapper;

import bubbles.springapibackend.domain.comment.Comment;
import bubbles.springapibackend.domain.comment.dto.CommentRequestDTO;
import bubbles.springapibackend.domain.comment.dto.CommentResponseDTO;
import bubbles.springapibackend.domain.user.User;
import bubbles.springapibackend.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CommentMapper {

    private final UserRepository userRepository;

    public CommentResponseDTO toDTO(Comment comment) {
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(comment.getId());
        dto.setDateTime(comment.getDateTime());
        dto.setContent(comment.getContent());
        dto.setAuthor(comment.getAuthor());
        return dto;
    }

    public Comment toEntity(CommentRequestDTO dto) {
        Comment comment = new Comment();
        comment.setDateTime(dto.getDateTime());
        comment.setContent(dto.getContent());
        Optional<User> authorOpt = userRepository.findById(dto.getAuthorId());
        authorOpt.ifPresent(comment::setAuthor);
        return comment;
    }
}
