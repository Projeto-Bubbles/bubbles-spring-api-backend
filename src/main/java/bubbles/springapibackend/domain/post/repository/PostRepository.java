package bubbles.springapibackend.domain.post.repository;

import bubbles.springapibackend.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByFkUserIdUser(Integer authorId);

    List<Post> findByFkUserNickname(String authorUsername);

    List<Post> findByFkBubbleTitle(String bubbleTitle);
}
