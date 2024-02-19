package bubbles.springapibackend.domain.bubble.repository;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.bubble.Bubble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BubbleRepository extends JpaRepository<Bubble, Integer> {
    List<Bubble> findAllByTitleContainsIgnoreCase(String bubbleHeadline);

    List<Bubble> findAllByCreationDateIsGreaterThanEqual(LocalDate bubbleCreationDate);

    List<Bubble> findAllByCreationDateIsLessThanEqual(LocalDate bubbleCreationDate);

    @Query("SELECT b FROM Bubble b WHERE (:bubbleCategories IS NULL OR b.category IN :bubbleCategories)")
    List<Bubble> findAllByCategory(@Param("bubbleCategories") List<Category> bubbleCategories);

    List<Bubble> findAllByCreatorId(Integer creatorId);

    List<Bubble> findAllByCreatorUsername(String creatorUsername);
}
