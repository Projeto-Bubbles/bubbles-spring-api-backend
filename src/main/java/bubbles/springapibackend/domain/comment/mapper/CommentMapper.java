package bubbles.springapibackend.domain.comment.mapper;

import bubbles.springapibackend.domain.comment.Comment;
import bubbles.springapibackend.domain.comment.dto.CommentRequestDTO;
import bubbles.springapibackend.domain.comment.dto.CommentResponseDTO;
import bubbles.springapibackend.domain.user.mapper.UserMapper;
import bubbles.springapibackend.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommentMapper {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public CommentResponseDTO toDTO(Comment comment) {
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setIdComment(comment.getIdComment());
        dto.setMoment(comment.getMoment());
        dto.setContents(comment.getContents());
        dto.setAuthor(userMapper.toUserInfoDTO(comment.getAuthor()));
        return dto;
    }

    public Comment toEntity(CommentRequestDTO dto) {
        Comment comment = new Comment();
        comment.setAuthor(userRepository.getUserByIdUser(dto.getIdAuthor()));
        comment.setMoment(dto.getMoment());
        comment.setContents(dto.getContent());
        return comment;
    }
}
