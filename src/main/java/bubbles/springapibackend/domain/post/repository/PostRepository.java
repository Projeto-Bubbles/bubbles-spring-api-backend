package bubbles.springapibackend.domain.post.repository;

import bubbles.springapibackend.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByAuthorId(Integer authorId);

    List<Post> findByAuthorNickname(String authorNickname);

    List<Post> findByBubbleHeadline(String bubbleHeadline);
}
