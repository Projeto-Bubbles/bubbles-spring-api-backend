package bubbles.springapibackend.domain.event;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EventInPerson extends Event {
    private boolean publicPlace;
    private Integer peopleCapacity;
    private String address;

    public EventInPerson(Integer id, String title, LocalDateTime date, String category, Integer duration, String author, String bubble, boolean publicPlace, Integer peopleCapacity, String address) {
        super(id, title, date, category, duration, author, bubble);
        this.publicPlace = publicPlace;
        this.peopleCapacity = peopleCapacity;
        this.address = address;
    }

    public EventInPerson(boolean publicPlace, Integer peopleCapacity, String address) {
        this.publicPlace = publicPlace;
        this.peopleCapacity = peopleCapacity;
        this.address = address;
    }

    @Override
    public String sendConfirmationCode() {
        return null;
    }
}
