package bubbles.springapibackend.domain.event.dto;

import bubbles.springapibackend.domain.address.Address;
import lombok.Data;

@Data
public class EventInPersonDTO extends EventDTO{
    private boolean publicPlace;
    private Integer peopleCapacity;
    private Address address;
}
