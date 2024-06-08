package bubbles.springapibackend.domain.event.dto;

import bubbles.springapibackend.domain.address.dto.AddressDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EventInPersonRequestDTO extends EventRequestDTO {
    private boolean publicPlace;
    private Integer peopleCapacity;
    private AddressDTO address;
}
