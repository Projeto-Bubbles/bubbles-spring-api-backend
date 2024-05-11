package bubbles.springapibackend.domain.post.repository;

import bubbles.springapibackend.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByAuthorIdUser(Integer authorId);

    List<Post> findByAuthorNickname(String authorUsername);

    List<Post> findByBubbleTitle(String bubbleTitle);
}
