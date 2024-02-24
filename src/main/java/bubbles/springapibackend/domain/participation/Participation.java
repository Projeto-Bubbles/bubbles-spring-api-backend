package bubbles.springapibackend.domain.participation;

import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.event.Event;
import bubbles.springapibackend.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_participation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idParticipant;

    @ManyToOne
    @JoinColumn(name = "fk_user")
    private User fkUser;

    @ManyToOne
    @JoinColumn(name = "fk_event")
    private Event fkEvent;
}
