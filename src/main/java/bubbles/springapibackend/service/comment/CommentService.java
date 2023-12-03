package bubbles.springapibackend.service.comment;

import bubbles.springapibackend.domain.comment.Comment;
import bubbles.springapibackend.domain.comment.dto.CommentRequestDTO;
import bubbles.springapibackend.domain.comment.mapper.CommentMapper;
import bubbles.springapibackend.domain.comment.repository.CommentRepository;
import bubbles.springapibackend.domain.post.Post;
import bubbles.springapibackend.domain.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    public List<Comment> getCommentsByPost(Integer postId) {
        return commentRepository.findByPostId(postId);
    }

    public Comment createComment(CommentRequestDTO commentRequestDTO) {
        Comment comment = commentMapper.toEntity(commentRequestDTO);
        comment.setDateTime(LocalDateTime.now());

        // Verifique se está associando corretamente ao post
        Post post = postRepository.findById(commentRequestDTO.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        comment.setPost(post);

        return commentRepository.save(comment);
    }
}


