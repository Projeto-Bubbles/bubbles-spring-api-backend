package bubbles.springapibackend.domain.event;

import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.participation.Participation;
import bubbles.springapibackend.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@DiscriminatorColumn(name = "event_type", columnDefinition = "VARCHAR(13)")
@Table(name = "tb_event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEvent;

    @Column(columnDefinition = "VARCHAR(100)")
    private String title;

    private LocalDateTime moment;

    private Integer duration;

    private String image;

    @ManyToOne
    @JoinColumn(name = "fk_user")
    private User organizer;

    @ManyToOne
    @JoinColumn(name = "fk_bubble")
    private Bubble bubble;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Participation> participants;

    public Event(Integer idEvent, String title, LocalDateTime moment, Integer duration, String image,
                 User organizer, Bubble bubble) {
        this.idEvent = idEvent;
        this.title = title;
        this.moment = moment;
        this.duration = duration;
        this.image = image;
        this.organizer = organizer;
        this.bubble = bubble;
    }
}
