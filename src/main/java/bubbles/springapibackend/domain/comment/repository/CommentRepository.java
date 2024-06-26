package bubbles.springapibackend.domain.comment.repository;

import bubbles.springapibackend.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByPostIdPost(Integer postId);

    List<Comment> findAllByAuthorIdUser(Integer authorId);
}
