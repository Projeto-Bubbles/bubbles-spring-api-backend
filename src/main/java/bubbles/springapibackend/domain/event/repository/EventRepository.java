package bubbles.springapibackend.domain.event.repository;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findAllByAuthorNickname(String author);

    List<Event> findByBubbleHeadline(String bubble);

    List<Event> findByAuthorId(Integer id);

    List<Event> findByBubbleId(Integer id);

    @Query("SELECT e FROM Event e WHERE (:categories IS NULL OR e.bubble.category IN :categories)")
    List<Event> findFilteredEvents(@Param("categories") List<Category> categories);
}


