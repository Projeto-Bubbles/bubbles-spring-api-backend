package bubbles.springapibackend.domain.event;

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
public class EventInPerson extends Event {
    private boolean publicPlace;
    private Integer peopleCapacity;

    private String address;

    @Override
    public String sendConfirmationCode() {
        return null;
    }
}
