package bubbles.springapibackend.service.address;

import bubbles.springapibackend.domain.address.Address;
import bubbles.springapibackend.domain.address.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }

    public Address getAddressById(Integer id) {
        return addressRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Endereço com ID: " + id + " não encontrado!"));
    }

    public Address getUserByCep(String addressCep) {
        return addressRepository.findByCep(addressCep);
    }

    public Address getUserByEstate(String addressEstate) {
        return addressRepository.findByEstate(addressEstate);
    }

    public Address getUserByCity(String addressCity) {
        return addressRepository.findByCity(addressCity);
    }

    public Address getUserByNeighborhood(String addressNeighborhood) {
        return addressRepository.findByNeighborhood(addressNeighborhood);
    }

    public Address getUserByStreet(String addressStreet) {
        return addressRepository.findByStreet(addressStreet);
    }

    public Address getUserByHouseNumber(String addressHouseNumber) {
        return addressRepository.findByHouseNumber(addressHouseNumber);
    }

    public Address registerAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address updateAddress(Address address) {
        return addressRepository.save(address);
    }

    public void deleteAddressById(Integer addressId) {
        addressRepository.deleteById(addressId);
    }
}
