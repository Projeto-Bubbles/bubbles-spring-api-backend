package bubbles.springapibackend.domain.event;

import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private Integer id;

    @Column(columnDefinition = "VARCHAR(100)")
    private String title;

    @Column(columnDefinition = "DATETIME")
    private LocalDateTime moment;


    private Integer duration;

    @ManyToOne
    private User author;

    @ManyToOne
    private Bubble bubble;
}
