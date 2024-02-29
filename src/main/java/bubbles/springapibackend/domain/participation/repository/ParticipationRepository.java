package bubbles.springapibackend.domain.participation.repository;

import bubbles.springapibackend.domain.participation.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Integer> {
    @Query("SELECT p.idParticipant, e.moment, e.title, b.title " +
            "FROM Participation p " +
            "JOIN p.event e " +
            "JOIN e.bubble b " +
            "WHERE p.user.idUser = :idUser " +
            "AND e.moment > CURRENT_TIMESTAMP " +
            "ORDER BY e.moment ASC " +
            "LIMIT 5")
    List<Participation> findNext5EventsByIdUser(Integer idUser);
}
