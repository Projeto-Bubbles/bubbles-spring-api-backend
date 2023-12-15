package bubbles.springapibackend.domain.bubble.repository;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.bubble.Bubble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BubbleRepository extends JpaRepository<Bubble, Integer> {
    List<Bubble> findByCreatorId(Integer id);

    @Query("SELECT b FROM Bubble b WHERE (:categories IS NULL OR b.category IN :categories)")
    List<Bubble> findAllByCategory(@Param("categories") List<Category> categories);

    List<Bubble> findAllByCreatorNickname(String nickname);

    Optional<Bubble> findByHeadline(String headline);
}
