package bubbles.springapibackend.domain.user.dto;

import lombok.Data;

@Data
public class UserInfoDTO {
    private Integer idUser;
    private String username;
    private String nickname;
    private String email;
}
