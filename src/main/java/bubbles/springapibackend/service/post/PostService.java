package bubbles.springapibackend.service.post;

import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.comment.Comment;
import bubbles.springapibackend.domain.comment.dto.CommentRequestDTO;
import bubbles.springapibackend.domain.comment.dto.CommentResponseDTO;
import bubbles.springapibackend.domain.comment.mapper.CommentMapper;
import bubbles.springapibackend.domain.post.Post;
import bubbles.springapibackend.domain.post.dto.PostRequestDTO;
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

    public List<CommentResponseDTO> getCommentsByPostId(Integer postId) {
        return commentService.getCommentsByPostId(postId).stream()
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

    public List<PostResponseDTO> getPostsByUserNickname(String userNickname) {
        return postRepository.findByAuthorNickname(userNickname).stream()
                .map(postMapper::toDTO).collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPostsByBubbleTitle(String bubbleTitle) {
        return postRepository.findByBubbleTitle(bubbleTitle).stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PostResponseDTO createPost(PostRequestDTO newPostDTO) {
        if (newPostDTO.getIdAuthor() == null || newPostDTO.getIdBubble() == null) {
            throw new IllegalArgumentException("fkUser ou fkBubble não podem ser nulo");
        }

        User user = userService.getUserById(newPostDTO.getIdAuthor());
        Bubble bubble = bubbleService.getBubbleById(newPostDTO.getIdBubble());

        Post newPost = new Post();
        newPost.setMoment(LocalDateTime.now());
        newPost.setContents(newPostDTO.getContents());
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
        existingPost.setContents(updatedPostDTO.getContents());

        return postMapper.toDTO(postRepository.save(existingPost));
    }

    public void deletePost(Integer postId) {
        postRepository.deleteById(postId);
    }
}

