package bubbles.springapibackend.domain.address.repository;

import bubbles.springapibackend.domain.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Address findByCep(String addressCep);
    Address findByEstate(String addressEstate);
    Address findByCity(String addressCity);
    Address findByNeighborhood(String addressNeighborhood);
    Address findByStreet(String addressStreet);
    Address findByHouseNumber(String addressHouseNumber);
}
