package bubbles.springapibackend.service.member;

import bubbles.springapibackend.domain.member.dto.MemberResponseDTO;
import bubbles.springapibackend.domain.member.mapper.MemberMapper;
import bubbles.springapibackend.domain.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public List<MemberResponseDTO> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(memberMapper::toDTO).collect(Collectors.toList());
    }
}