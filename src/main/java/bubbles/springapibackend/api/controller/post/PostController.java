package bubbles.springapibackend.api.controller.post;

import bubbles.springapibackend.api.util.Stack;
import bubbles.springapibackend.domain.comment.dto.CommentRequestDTO;
import bubbles.springapibackend.domain.comment.dto.CommentResponseDTO;
import bubbles.springapibackend.domain.post.Post;
import bubbles.springapibackend.domain.post.dto.PostRequestDTO;
import bubbles.springapibackend.domain.post.dto.PostResponseDTO;
import bubbles.springapibackend.domain.post.mapper.PostMapper;
import bubbles.springapibackend.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private PostMapper postMapper;

    @Operation(summary = "Get Comments for a Post",
            description = "Returns a list of comments for a specific post.")
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByPost(
            @Parameter(description = "Unique post ID") @PathVariable Integer postId) {
        List<CommentResponseDTO> comments = postService.getCommentsByPost(postId);
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(comments);
        }
    }

    @Operation(summary = "Get All Posts",
            description = "Returns a list of all posts.")
    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getPosts() {
        List<PostResponseDTO> posts = postService.getPosts();

        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Stack<PostResponseDTO> postStack = new Stack<>(posts.size());

        for (PostResponseDTO post : posts) {
            postStack.push(post);
        }

        List<PostResponseDTO> postsInStack = new ArrayList<>();

        while (!postStack.isEmpty()) {
            postsInStack.add(postStack.pop());
        }

        return ResponseEntity.ok(postsInStack);
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
    public ResponseEntity<List<PostResponseDTO>> getByAuthor(
            @Parameter(description = "Author name") @RequestParam String author) {
        List<PostResponseDTO> posts = postService.getPostsByAuthor(author);
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(posts);
        }
    }

    @Operation(summary = "Get Posts by Bubble",
            description = "Returns posts associated with a specific bubble (group).")
    @GetMapping("/bubble")
    public ResponseEntity<List<PostResponseDTO>> getByBubble(
            @Parameter(description = "Bubble (group) name") @RequestParam String bubble) {
        List<PostResponseDTO> posts = postService.getPostsByBubble(bubble);
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(posts);
        }
    }

    @Operation(summary = "Create Post",
            description = "Create a new post.")
    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(
            @Parameter(description = "JSON object representing the new post",
                    required = true) @Validated @RequestBody PostRequestDTO newPostDTO) {
        PostResponseDTO createdPost = postService.createPost(newPostDTO);
        return ResponseEntity.ok(createdPost);
    }

    @Operation(summary = "Create Comment for a Post",
            description = "Create a new comment for a specific post.")
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponseDTO> createCommentForPost(
            @Parameter(description = "Unique post ID") @PathVariable Integer postId,
            @Parameter(description = "JSON object representing the new comment",
                    required = true) @Validated @RequestBody CommentRequestDTO newCommentDTO) {
        CommentResponseDTO createdComment = postService.createCommentForPost(postId, newCommentDTO);

        return ResponseEntity.created(null).body(createdComment);
    }

    @Operation(summary = "Edit Post",
            description = "Edit an existing post.")
    @PatchMapping("/{id}")
    public ResponseEntity<PostResponseDTO> editPost(
            @Parameter(description = "Unique post ID") @PathVariable Integer id,
            @Parameter(description = "JSON object representing the updated post",
                    required = true) @Validated @RequestBody PostRequestDTO updatedPostDTO) {
        PostResponseDTO updatedPost = postService.editPost(id, updatedPostDTO);

        return ResponseEntity.ok().body(updatedPost);
    }

    @Operation(summary = "Delete Post",
            description = "Delete a post by its unique ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @Parameter(description = "Unique post ID") @PathVariable Integer id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
