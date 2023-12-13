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
import org.springframework.stereotype.Service;

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
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Post getPostById(Integer id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post with ID " + id + " not " +
                        "found"));
    }

    public List<PostResponseDTO> getPostsByAuthor(String author) {
        return postRepository.findByAuthorUsername(author).stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPostsByBubble(String bubble) {
        return postRepository.findByBubbleHeadline(bubble).stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PostResponseDTO createPost(PostRequestDTO newPostDTO) {
        Integer userId = newPostDTO.getAuthorId();
        Integer bubbleId = newPostDTO.getBubbleId();
        User user = userService.getUserById(userId);
        Bubble bubble = bubbleService.getBubbleById(bubbleId);

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

    public PostResponseDTO editPost(Integer id, PostRequestDTO updatedPostDTO) {
        Optional<Post> existingPostOpt = postRepository.findById(id);

        if (existingPostOpt.isPresent()) {
            Post existingPost = existingPostOpt.get();
            existingPost.setMoment(LocalDateTime.now());
            existingPost.setContent(updatedPostDTO.getContent());

            Post updatedPost = postRepository.save(existingPost);
            return postMapper.toDTO(updatedPost);
        } else {
            throw new EntityNotFoundException("Post with ID " + id + " not found");
        }
    }

    public void deletePost(Integer id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Post with ID " + id + " not found");
        }
    }
}

