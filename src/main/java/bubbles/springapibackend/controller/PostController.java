package bubbles.springapibackend.controller;

import bubbles.springapibackend.entity.Post;
import bubbles.springapibackend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = this.postRepository.findAll();

        if(posts.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable UUID id) {
        Optional<Post> postOpt = this.postRepository.findById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/author")
    public ResponseEntity<List<Post>> getByAuthor(@RequestParam String author) {
        List<Post> posts = this.postRepository.findByAuthor(author);

        if(posts.isEmpty()){
            return  ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/bubble")
    public ResponseEntity<List<Post>> getByBubble(@RequestParam String bubble) {
        List<Post> posts = this.postRepository.findByBubble(bubble);

        if(posts.isEmpty()){
            return  ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(posts);
    }

    @PostMapping("/post")
    public ResponseEntity<Post> createPost(@Validated @RequestBody Post newPost) {
        Post savedPost = postRepository.save(newPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> editPost(@PathVariable UUID id, @Validated @RequestBody Post updatedPost) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        Optional<Post> existingPostOpt = postRepository.findById(id);

        if (existingPostOpt.isPresent()) {
            postRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
