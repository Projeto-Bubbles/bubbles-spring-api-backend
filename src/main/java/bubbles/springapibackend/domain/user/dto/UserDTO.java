package bubbles.springapibackend.domain.user.dto;

import lombok.*;

@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private String cpf;
    private String nickname;
    private String password;
    private String image;
    private String userCover;
}
