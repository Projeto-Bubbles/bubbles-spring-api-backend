package bubbles.springapibackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_eventInPerson")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventInPerson extends Event {
    private boolean publicPlace;
    private Integer peopleCapacity;

    @OneToOne
    private Address address;

    @Override
    public String sendConfirmationCode() {
        return null;
    }
}
