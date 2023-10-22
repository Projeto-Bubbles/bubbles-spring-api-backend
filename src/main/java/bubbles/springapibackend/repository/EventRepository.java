package bubbles.springapibackend.repository;

import bubbles.springapibackend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByAuthor(String author);

    @Query("SELECT e FROM Event e WHERE e.date >= :currentDate")
    List<Event> findAvailableEvents(LocalDateTime currentDate);

    List<Event> findByBubble(String bubble);

    Optional<Event> findById(Integer id);
}
