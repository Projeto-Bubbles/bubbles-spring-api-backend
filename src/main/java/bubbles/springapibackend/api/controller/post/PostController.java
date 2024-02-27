package bubbles.springapibackend.api.controller.post;

import bubbles.springapibackend.domain.comment.dto.CommentRequestDTO;
import bubbles.springapibackend.domain.comment.dto.CommentResponseDTO;
import bubbles.springapibackend.domain.post.Post;
import bubbles.springapibackend.domain.post.dto.PostRequestDTO;
import bubbles.springapibackend.domain.post.dto.PostResponseDTO;
import bubbles.springapibackend.domain.post.mapper.PostMapper;
import bubbles.springapibackend.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostMapper postMapper;

    @Operation(summary = "Get Comments for a Post",
            description = "Returns a list of comments for a specific post.")
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByPostId(
            @Parameter(description = "Unique post ID") @PathVariable Integer postId) {
        List<CommentResponseDTO> comments = postService.getCommentsByPostId(postId);
        if (comments.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Get All Posts", description = "Returns a list of all posts.")
    @GetMapping()
    public ResponseEntity<List<PostResponseDTO>> getPosts() {
        List<PostResponseDTO> posts = postService.getPosts();

        if (posts.isEmpty()) return ResponseEntity.noContent().build();

        List<PostResponseDTO> postsDTO = posts.stream()
                .sorted(Comparator.comparing(PostResponseDTO::getIdPost))
                .collect(Collectors.toList());

        return ResponseEntity.ok(postsDTO);
    }

    @Operation(summary = "Get Post by ID",
            description = "Returns a post by its unique ID.")
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getById(
            @Parameter(description = "Unique post ID") @PathVariable Integer id) {
        Post post = postService.getPostById(id);
        PostResponseDTO postResponseDTO = postMapper.toDTO(post);
        return ResponseEntity.ok(postResponseDTO);
    }

    @Operation(summary = "Get Posts by Author",
            description = "Returns posts associated with a specific author.")
    @GetMapping("/author")
    public ResponseEntity<List<PostResponseDTO>> getByUserNickname(
            @Parameter(description = "Author name") @RequestParam String userNickname) {
        List<PostResponseDTO> posts = postService.getPostsByUserNickname(userNickname);
        if (posts.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "Get Posts by Bubble",
            description = "Returns posts associated with a specific bubble (group).")
    @GetMapping("/bubble")
    public ResponseEntity<List<PostResponseDTO>> getByBubbleTitle(
            @Parameter(description = "Bubble (group) name") @RequestParam String bubbleTitle) {
        List<PostResponseDTO> posts = postService.getPostsByBubbleTitle(bubbleTitle);
        if (posts.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "Create Post", description = "Create a new post.")
    @PostMapping()
    public ResponseEntity<PostResponseDTO> createPost(
            @Parameter(description = "JSON object representing the new post",
                    required = true) @Validated @RequestBody PostRequestDTO newPostDTO) {
        PostResponseDTO createdPost = postService.createPost(newPostDTO);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @Operation(summary = "Create Comment for a Post",
            description = "Create a new comment for a specific post.")
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponseDTO> createCommentForPost(
            @Parameter(description = "Unique post ID") @PathVariable Integer postId,
            @Parameter(description = "JSON object representing the new comment",
                    required = true) @Validated @RequestBody CommentRequestDTO newCommentDTO) {
        CommentResponseDTO createdComment = postService.createCommentForPost(postId, newCommentDTO);

        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @Operation(summary = "Edit Post",
            description = "Edit an existing post.")
    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> editPost(
            @Parameter(description = "Unique post ID") @PathVariable Integer postId,
            @Parameter(description = "JSON object representing the updated post",
                    required = true) @Validated @RequestBody PostResponseDTO updatedPostDTO) {
        PostResponseDTO updatedPost = postService.editPost(postId, updatedPostDTO);
        return ResponseEntity.ok().body(updatedPost);
    }

    @Operation(summary = "Delete Post",
            description = "Delete a post by its unique ID.")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @Parameter(description = "Unique post ID") @PathVariable Integer postId) {
        if (postService.getPostById(postId) == null) return ResponseEntity.notFound().build();
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
