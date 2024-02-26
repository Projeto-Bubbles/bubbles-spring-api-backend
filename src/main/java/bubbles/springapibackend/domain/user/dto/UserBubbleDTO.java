package bubbles.springapibackend.domain.user.dto;

import lombok.Data;

@Data
public class UserBubbleDTO {
    private Integer idUser;
    private String username;
    private String nickname;
    private String email;
}
