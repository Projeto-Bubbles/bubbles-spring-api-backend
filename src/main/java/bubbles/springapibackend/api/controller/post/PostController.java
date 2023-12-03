package bubbles.springapibackend.api.controller.post;

import bubbles.springapibackend.domain.comment.Comment;
import bubbles.springapibackend.domain.comment.dto.CommentRequestDTO;
import bubbles.springapibackend.domain.comment.dto.CommentResponseDTO;
import bubbles.springapibackend.domain.comment.mapper.CommentMapper;
import bubbles.springapibackend.domain.comment.repository.CommentRepository;
import bubbles.springapibackend.domain.post.Post;
import bubbles.springapibackend.domain.post.dto.PostRequestDTO;
import bubbles.springapibackend.domain.post.dto.PostResponseDTO;
import bubbles.springapibackend.domain.post.mapper.PostMapper;
import bubbles.springapibackend.domain.post.repository.PostRepository;
import bubbles.springapibackend.service.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final PostMapper postMapper;

    @Operation(summary = "Get Comments for a Post",
            description = "Returns a list of comments for a specific post.")
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByPost(
            @Parameter(description = "Unique post ID") @PathVariable Integer postId) {

        List<Comment> comments = commentService.getCommentsByPost(postId);

        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CommentResponseDTO> commentDTOs = comments.stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(commentDTOs);
    }


    @Operation(summary = "Get All Posts",
            description = "Returns a list of all posts.")
    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getPosts() {
        List<PostResponseDTO> posts = postRepository.findAll().stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());

        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "Get Post by ID",
            description = "Returns a post by its unique ID.")
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getById(
            @Parameter(description = "Unique post ID") @PathVariable Integer id) {
        Optional<Post> postOpt = this.postRepository.findById(id);

        if (postOpt.isPresent()) {
            return ResponseEntity.ok(postMapper.toDTO(postOpt.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get Posts by Author",
            description = "Returns posts authored by a specific user.")
    @GetMapping("/author")
    public ResponseEntity<List<PostResponseDTO>> getByAuthor(
            @Parameter(description = "Author's name") @RequestParam String author) {
        List<PostResponseDTO> posts = postRepository.findByAuthor(author).stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());

        if (posts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "Get Posts by Bubble",
            description = "Returns posts associated with a specific bubble (group).")
    @GetMapping("/bubble")
    public ResponseEntity<List<PostResponseDTO>> getByBubble(
            @Parameter(description = "Bubble (group) name") @RequestParam String bubble) {
        List<PostResponseDTO> posts = postRepository.findByBubble(bubble).stream()
                .map(postMapper::toDTO)
                .collect(Collectors.toList());

        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "Create Post",
            description = "Create a new post.")
    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(
            @Parameter(description = "JSON object representing the new post",
                    required = true) @Validated @RequestBody PostRequestDTO newPostDTO) {
        Post newPost = postMapper.toEntity(newPostDTO);
        newPost.setDateTime(LocalDateTime.now()); // Define a data e hora do post
        Post savedPost = postRepository.save(newPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(postMapper.toDTO(savedPost));
    }

    @Operation(summary = "Create Comment for a Post",
            description = "Create a new comment for a specific post.")
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponseDTO> createCommentForPost(
            @Parameter(description = "Unique post ID") @PathVariable Integer postId,
            @Parameter(description = "JSON object representing the new comment",
                    required = true) @Validated @RequestBody CommentRequestDTO newCommentDTO) {

        Optional<Post> postOpt = postRepository.findById(postId);

        if (postOpt.isPresent()) {

            Comment savedComment = commentService.createComment(newCommentDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.toDTO(savedComment));
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Restante das rotas...

    @Operation(summary = "Edit Post",
            description = "Edit an existing post.")
    @PatchMapping("/{id}")
    public ResponseEntity<PostResponseDTO> editPost(
            @Parameter(description = "Unique post ID") @PathVariable Integer id,
            @Parameter(description = "JSON object representing the updated post",
                    required = true) @Validated @RequestBody PostRequestDTO updatedPostDTO) {
        Optional<Post> existingPostOpt = postRepository.findById(id);

        if (existingPostOpt.isPresent()) {
            Post existingPost = existingPostOpt.get();
            existingPost.setDateTime(LocalDateTime.now());
            existingPost.setContent(updatedPostDTO.getContent());

            Post updatedPost = postRepository.save(existingPost);

            return ResponseEntity.ok(postMapper.toDTO(updatedPost));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete Post",
            description = "Delete a post by its unique ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @Parameter(description = "Unique post ID") @PathVariable Integer id) {
        Optional<Post> existingPostOpt = postRepository.findById(id);

        if (existingPostOpt.isPresent()) {
            postRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
