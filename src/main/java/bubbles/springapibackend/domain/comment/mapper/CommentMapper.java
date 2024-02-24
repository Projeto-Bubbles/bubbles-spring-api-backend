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
        dto.setId(comment.getIdComment());
        dto.setDateTime(comment.getMoment());
        dto.setContent(comment.getContents());
        dto.setAuthor(comment.getFkUser().getNickname());
        return dto;
    }

    public Comment toEntity(CommentRequestDTO dto) {
        Comment comment = new Comment();
        comment.setMoment(dto.getDateTime());
        comment.setContents(dto.getContent());
        Optional<User> authorOpt = userRepository.findById(dto.getAuthorId());
        authorOpt.ifPresent(comment::setFkUser);
        return comment;
    }
}
