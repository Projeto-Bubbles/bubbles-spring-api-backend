package bubbles.springapibackend.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventOnline extends Event{
    private String platform;
    private String url;

    @Override
    public String sendConfirmationCode() {
        return null;
    }
}
