package bubbles.springapibackend.domain.event.repository;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByBubbleHeadline(String bubbleHeadline);

    @Query("SELECT e FROM Event e WHERE (:bubbleCategories IS NULL OR e.bubble.category IN :bubbleCategories)")
    List<Event> findAllByBubbleCategory(@Param("bubbleCategories") List<Category> bubbleCategories);

    List<Event> findAllByCreatorId(Integer creatorId);

    List<Event> findAllByCreatorNickname(String creatorNickname);

    List<Event> findAllByBubbleId(Integer bubbleId);
}


