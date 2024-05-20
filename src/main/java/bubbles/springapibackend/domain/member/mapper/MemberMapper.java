package bubbles.springapibackend.domain.member.mapper;

import bubbles.springapibackend.domain.bubble.mapper.BubbleMapper;
import bubbles.springapibackend.domain.member.Member;
import bubbles.springapibackend.domain.member.dto.MemberResponseDTO;
import bubbles.springapibackend.domain.user.mapper.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    private final BubbleMapper bubbleMapper;
    private final UserMapper userMapper;

    @Autowired
    public MemberMapper(BubbleMapper bubbleMapper, UserMapper userMapper) {
        this.bubbleMapper = bubbleMapper;
        this.userMapper = userMapper;
    }

    public MemberResponseDTO toDTO(Member member) {
        if (member == null || member.getFkUser() == null ||
                member.getFkBubble() == null) {
            throw new EntityNotFoundException("Inscrição ou membro nulo!");
        }

        MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
        memberResponseDTO.setIdMember(member.getIdMember());
        memberResponseDTO.setParticipant(userMapper.toUserInfoDTO(member.getFkUser()));
        memberResponseDTO.setBubble(bubbleMapper.toDTO(member.getFkBubble()));
        return memberResponseDTO;
    }
}
