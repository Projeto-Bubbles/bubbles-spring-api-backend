package bubbles.springapibackend.domain.event;

import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EventOnline extends Event{
    @Column(columnDefinition = "VARCHAR(100)")
    private String platform;

    @Column(columnDefinition = "VARCHAR(300)")
    private String link;

    public EventOnline(Integer id, String title, LocalDateTime date, Integer duration, User organizer,
                       Bubble bubble, String platform, String url) {
        super(id, title, date, duration, organizer, bubble);
        this.platform = platform;
        this.link = url;
    }
}
