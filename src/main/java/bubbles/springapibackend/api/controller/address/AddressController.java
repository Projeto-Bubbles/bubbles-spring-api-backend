package bubbles.springapibackend.api.controller.address;

import bubbles.springapibackend.domain.address.Address;
import bubbles.springapibackend.domain.address.dto.AddressDTO;
import bubbles.springapibackend.domain.address.mapper.AddressMapper;
import bubbles.springapibackend.domain.user.dto.UserDTO;
import bubbles.springapibackend.service.address.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {
    AddressService addressService;
    AddressMapper addressMapper;

    @Autowired
    public AddressController(AddressService addressService, AddressMapper addressMapper) {
        this.addressService = addressService;
        this.addressMapper = addressMapper;
    }

    @GetMapping()
    @Operation(summary = "Get Available Address", description = "Returns all address for the current date or in the future.")
    public ResponseEntity<List<AddressDTO>> getAvailableAddress() {
        List<Address> address = addressService.getAllAddress();

        if (address.isEmpty()) return ResponseEntity.notFound().build();

        List<AddressDTO> addressDTOS = address.stream().map(addressMapper::toDTO)
                .sorted(Comparator.comparing(AddressDTO::getCep)).collect(Collectors.toList());

        return ResponseEntity.ok(addressDTOS);
    }

    @Operation(summary = "Create Address", description = "Create a new address.")
    @PostMapping()
    public ResponseEntity<AddressDTO> registerAddress(@Validated @RequestBody AddressDTO newAddressDTO) {
        Address savedAddress = addressMapper.toEntity(newAddressDTO);
        savedAddress = addressService.registerAddress(savedAddress);
        newAddressDTO = addressMapper.toDTO(savedAddress);
        return new ResponseEntity<>(newAddressDTO, HttpStatus.CREATED);
    }
}
