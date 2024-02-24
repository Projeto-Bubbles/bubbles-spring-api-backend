package bubbles.springapibackend.service.comment;

import bubbles.springapibackend.domain.comment.Comment;
import bubbles.springapibackend.domain.comment.dto.CommentRequestDTO;
import bubbles.springapibackend.domain.comment.mapper.CommentMapper;
import bubbles.springapibackend.domain.comment.repository.CommentRepository;
import bubbles.springapibackend.domain.post.Post;
import bubbles.springapibackend.domain.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    public List<Comment> getCommentsByPostId(Integer postId) {
        return commentRepository.findAllByFkPostIdPost(postId);
    }

    public Comment createComment(CommentRequestDTO commentRequestDTO, Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        Comment comment = commentMapper.toEntity(commentRequestDTO);
        comment.setMoment(LocalDateTime.now());
        comment.setFkPost(post);

        return commentRepository.save(comment);
    }
}


