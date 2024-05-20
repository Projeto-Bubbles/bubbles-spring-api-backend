package bubbles.springapibackend.domain.member.dto;

import bubbles.springapibackend.domain.bubble.dto.BubbleResponseDTO;
import bubbles.springapibackend.domain.user.dto.UserInfoDTO;
import lombok.Data;

@Data
public class MemberResponseDTO {
    Integer idMember;
    UserInfoDTO participant;
    BubbleResponseDTO bubble;
}