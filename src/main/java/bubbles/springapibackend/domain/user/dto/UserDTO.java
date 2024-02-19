package bubbles.springapibackend.domain.user.dto;

import lombok.*;

@Data
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private String cpf;
    private String username;
    private String password;
}
