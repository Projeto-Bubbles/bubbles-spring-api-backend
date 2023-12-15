package bubbles.springapibackend.domain.bubble.repository;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.bubble.Bubble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BubbleRepository extends JpaRepository<Bubble, Integer> {
    List<Bubble> findAllByHeadlineContainsIgnoreCase(String bubbleHeadline);

    @Query("SELECT b FROM Bubble b WHERE (:bubbleCategories IS NULL OR b.category IN :bubbleCategories)")
    List<Bubble> findAllByCategory(@Param("bubbleCategories") List<Category> bubbleCategories);

    List<Bubble> findAllByCreatorId(Integer creatorId);

    List<Bubble> findAllByCreatorNickname(String creatorNickname);
}
