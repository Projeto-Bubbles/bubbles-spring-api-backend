package bubbles.springapibackend.domain.participation.repository;

import bubbles.springapibackend.domain.participation.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Integer> {
    @Query("SELECT p FROM Participation p WHERE p.user.idUser = :idUser " +
            "AND p.event.moment > CURRENT_TIMESTAMP ORDER BY p.event.moment ASC LIMIT 5")
    List<Participation> findNext5EventsByIdUser(Integer idUser);
}
