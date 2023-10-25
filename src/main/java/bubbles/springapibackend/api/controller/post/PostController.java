package bubbles.springapibackend.api.controller.post;

import bubbles.springapibackend.domain.post.Post;
import bubbles.springapibackend.domain.post.repository.PostRepository;
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

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    @Operation(summary = "Get All Posts",
            description = "Returns a list of all posts.")
    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = this.postRepository.findAll();

        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "Get Post by ID",
            description = "Returns a post by its unique ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Post>> getById(
            @Parameter(description = "Unique post ID") @PathVariable Integer id) {
        Optional<Post> postOpt = this.postRepository.findById(id);
        return ResponseEntity.ok().body(postOpt);
    }

    @Operation(summary = "Get Posts by Author",
            description = "Returns posts authored by a specific user.")
    @GetMapping("/author")
    public ResponseEntity<List<Post>> getByAuthor(
            @Parameter(description = "Author's name") @RequestParam String author) {
        List<Post> posts = this.postRepository.findByAuthor(author);

        if (posts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "Get Posts by Bubble",
            description = "Returns posts associated with a specific bubble (group).")
    @GetMapping("/bubble")
    public ResponseEntity<List<Post>> getByBubble(
            @Parameter(description = "Bubble (group) name") @RequestParam String bubble) {
        List<Post> posts = this.postRepository.findByBubble(bubble);

        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "Create Post",
            description = "Create a new post.")
    @PostMapping
    public ResponseEntity<Post> createPost(
            @Parameter(description = "JSON object representing the new post",
                    required = true) @Validated @RequestBody Post newPost) {
        newPost.setDateTime(LocalDateTime.now()); // Define a data e hora do post
        Post savedPost = postRepository.save(newPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }

    @Operation(summary = "Edit Post",
            description = "Edit an existing post.")
    @PatchMapping("/{id}")
    public ResponseEntity<Post> editPost(
            @Parameter(description = "Unique post ID") @PathVariable Integer id,
            @Parameter(description = "JSON object representing the updated post",
                    required = true) @Validated @RequestBody Post updatedPost) {
        Optional<Post> existingPostOpt = postRepository.findById(id);

        if (existingPostOpt.isPresent()) {
            Post existingPost = existingPostOpt.get();
            existingPost.setDateTime(LocalDateTime.now());
            existingPost.setContent(updatedPost.getContent());

            updatedPost = postRepository.save(existingPost);

            return ResponseEntity.ok(updatedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Submit comment",
                description = "Submit comment to the post")
    @PutMapping("/comment/send/{postID}/{comment}")
    public ResponseEntity<Post> submitComment(
            @Parameter(description = "Unique post id") @PathVariable int postID,
            @Parameter(description = "Comment content") @PathVariable String comment){

        return null;
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
