package bubbles.springapibackend.repository;

import bubbles.springapibackend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByAuthor_Name(String author);

    List<Event> findByBubble_Name(String bubble);

    Optional<Event> findById(Integer id);
}
