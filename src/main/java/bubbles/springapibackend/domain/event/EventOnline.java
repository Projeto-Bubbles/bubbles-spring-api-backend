package bubbles.springapibackend.domain.event;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EventOnline extends Event{
    private String platform;
    private String url;

    public EventOnline(Integer id, String title, LocalDateTime date, String category, Integer duration, String author, String bubble, String platform, String url) {
        super(id, title, date, category, duration, author, bubble);
        this.platform = platform;
        this.url = url;
    }

    @Override
    public String sendConfirmationCode() {
        return null;
    }
}
