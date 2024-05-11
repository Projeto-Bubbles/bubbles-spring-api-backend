package bubbles.springapibackend.domain.address.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private String cep;
    private String estate;
    private String city;
    private String neighborhood;
    private String street;
    private String houseNumber;
}
