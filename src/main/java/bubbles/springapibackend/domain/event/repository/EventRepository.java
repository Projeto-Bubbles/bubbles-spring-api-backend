package bubbles.springapibackend.domain.event.repository;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByBubbleTitle(String bubbleTitle);

    @Query("SELECT e FROM Event e WHERE (:bubbleCategories IS NULL " +
            "OR e.bubble.category IN :bubbleCategories)")
    List<Event> findAllByBubbleCategory(@Param("bubbleCategories") List<Category> bubbleCategories);

    List<Event> findAllByOrganizerIdUser(Integer userId);

    List<Event> findAllByOrganizerNickname(String userNickname);

    List<Event> findAllByBubbleIdBubble(Integer bubbleId);
}


