package bubbles.springapibackend.domain.address.mapper;

import bubbles.springapibackend.domain.address.Address;
import bubbles.springapibackend.domain.address.dto.AddressDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressDTO toDTO(Address address) {
        if (address == null) {
            throw new EntityNotFoundException("Endereço nulo");
        }

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCep(address.getCep());
        addressDTO.setEstate(address.getEstate());
        addressDTO.setCity(address.getEstate());
        addressDTO.setNeighborhood(address.getNeighborhood());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setHouseNumber(address.getHouseNumber());

        return addressDTO;
    }

    public Address toEntity(AddressDTO addressDTO){
        if (addressDTO == null) {
            throw new EntityNotFoundException("Endereço nulo");
        }

        Address address = new Address();
        address.setCep(addressDTO.getCep());
        address.setEstate(addressDTO.getEstate());
        address.setCity(addressDTO.getCity());
        address.setNeighborhood(addressDTO.getNeighborhood());
        address.setStreet(addressDTO.getStreet());
        address.setHouseNumber(addressDTO.getHouseNumber());

        return address;
    }
}
