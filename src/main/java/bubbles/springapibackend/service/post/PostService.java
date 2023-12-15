package bubbles.springapibackend.service.post;

import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.comment.Comment;
import bubbles.springapibackend.domain.comment.dto.CommentRequestDTO;
import bubbles.springapibackend.domain.comment.dto.CommentResponseDTO;
import bubbles.springapibackend.domain.comment.mapper.CommentMapper;
import bubbles.springapibackend.domain.post.Post;
import bubbles.springapibackend.domain.post.dto.PostResponseDTO;
import bubbles.springapibackend.domain.post.mapper.PostMapper;
import bubbles.springapibackend.domain.post.repository.PostRepository;
import bubbles.springapibackend.domain.user.User;
import bubbles.springapibackend.service.bubble.BubbleService;
import bubbles.springapibackend.service.comment.CommentService;
import bubbles.springapibackend.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentService commentService;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final UserService userService;
    private final BubbleService bubbleService;

    public List<CommentResponseDTO> getCommentsByPost(Integer postId) {
        return commentService.getCommentsByPost(postId).stream()
                .map(commentMapper::toDTO).collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::toDTO).collect(Collectors.toList());
    }

    public Post getPostById(Integer postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Post com ID: " + postId + " não encontrado!"));
    }

    public List<PostResponseDTO> getPostsByAuthor(String author) {
        return postRepository.findByAuthorNickname(author).stream()
                .map(postMapper::toDTO).collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPostsByBubble(String bubble) {
        return postRepository.findByBubbleHeadline(bubble).stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PostResponseDTO createPost(PostResponseDTO newPostDTO) {
        User user = userService.getUserByNickname(newPostDTO.getAuthor());
        Bubble bubble = bubbleService.getBubbleById(newPostDTO.getBubbleId());

        Post newPost = new Post();
        newPost.setMoment(LocalDateTime.now());
        newPost.setContent(newPostDTO.getContent());
        newPost.setAuthor(user);
        newPost.setBubble(bubble);

        Post savedPost = postRepository.save(newPost);
        return postMapper.toDTO(savedPost);
    }

    public CommentResponseDTO createCommentForPost(Integer postId,
                                                   CommentRequestDTO newCommentDTO) {
        Optional<Post> postOpt = postRepository.findById(postId);

        if (postOpt.isPresent()) {
            Comment savedComment = commentService.createComment(newCommentDTO, postId);
            return commentMapper.toDTO(savedComment);
        } else {
            throw new EntityNotFoundException("Post with ID " + postId + " not found");
        }
    }

    public PostResponseDTO editPost(Integer postId, PostResponseDTO updatedPostDTO) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Bolha com ID: " + postId + " não encontrado!"));

        existingPost.setMoment(LocalDateTime.now());
        existingPost.setContent(updatedPostDTO.getContent());

        return postMapper.toDTO(postRepository.save(existingPost));
    }

    public void deletePost(Integer postId) {
        postRepository.deleteById(postId);
    }
}

