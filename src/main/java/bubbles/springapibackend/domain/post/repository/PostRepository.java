package bubbles.springapibackend.domain.post.repository;

import bubbles.springapibackend.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByAuthorUsername(String author);

    List<Post> findByAuthorId(Integer id);

    List<Post> findByBubbleHeadline(String bubble);

    Optional<Post> findById(Integer id);
}
