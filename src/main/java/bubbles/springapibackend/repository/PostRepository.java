package bubbles.springapibackend.repository;

import bubbles.springapibackend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByAuthor_Name(String author);
    List<Post> findByBubble_Name(String bubble);

    Optional<Post> findById(Integer id);
}
