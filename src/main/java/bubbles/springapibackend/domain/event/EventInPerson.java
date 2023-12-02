package bubbles.springapibackend.domain.event;

import bubbles.springapibackend.domain.address.Address;
import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EventInPerson extends Event {
    private boolean publicPlace;
    private Integer peopleCapacity;

    @OneToOne
    private Address address;

    public EventInPerson(Integer id, String title, LocalDateTime date, Integer duration, User author,
                         Bubble bubble, boolean publicPlace, Integer peopleCapacity, Address address) {
        super(id, title, date, duration, author, bubble);
        this.publicPlace = publicPlace;
        this.peopleCapacity = peopleCapacity;
        this.address = address;
    }

    public EventInPerson(boolean publicPlace, Integer peopleCapacity, Address address) {
        this.publicPlace = publicPlace;
        this.peopleCapacity = peopleCapacity;
        this.address = address;
    }
}
