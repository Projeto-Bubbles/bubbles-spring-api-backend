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
public class EventInPerson extends Event {
    private boolean publicPlace;
    private Integer peopleCapacity;
    private Address address;

    @Override
    public String sendConfirmationCode() {
        return null;
    }
}