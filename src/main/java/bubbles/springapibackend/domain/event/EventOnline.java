package bubbles.springapibackend.domain.event;

import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.user.User;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EventOnline extends Event{
    private String platform;
    private String url;

    public EventOnline(Integer id, String title, LocalDateTime date, Integer duration, User author,
                       Bubble bubble, String platform, String url) {
        super(id, title, date, duration, author, bubble);
        this.platform = platform;
        this.url = url;
    }
}
