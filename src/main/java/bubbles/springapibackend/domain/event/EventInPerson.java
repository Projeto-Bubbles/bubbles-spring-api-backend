package bubbles.springapibackend.domain.event;

import bubbles.springapibackend.domain.address.Address;
import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventInPerson extends Event {
    private boolean publicPlace;

    private Integer peopleCapacity;

    @ManyToOne
    @JoinColumn(name = "fk_address")
    private Address address;

    public EventInPerson(Integer id, String title, LocalDateTime date, Integer duration, User organizer,
                         Bubble bubble, boolean publicPlace,
                         Integer peopleCapacity, Address address) {
        super(id, title, date, duration, organizer, bubble);
        this.publicPlace = publicPlace;
        this.peopleCapacity = peopleCapacity;
        this.address = address;
    }
}
