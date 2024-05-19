package bubbles.springapibackend.service.member;

import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.bubble.dto.BubbleResponseDTO;
import bubbles.springapibackend.domain.bubble.mapper.BubbleMapper;
import bubbles.springapibackend.domain.bubble.repository.BubbleRepository;
import bubbles.springapibackend.domain.member.Member;
import bubbles.springapibackend.domain.member.repository.MemberRepository;
import bubbles.springapibackend.domain.user.User;
import bubbles.springapibackend.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BubbleRepository bubbleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BubbleMapper bubbleMapper;

    public List<BubbleResponseDTO> getBubblesForUser(Integer userId) {
        List<Member> members = memberRepository.findByFkUserIdUser(userId);
        return members.stream()
                .map(member -> member.getFkBubble()) // Obtém a bolha diretamente do membro
                .map(bubbleMapper::toDTO) // Mapeia a bolha para BubbleResponseDTO
                .collect(Collectors.toList());
    }


    public void addUserToBubble(Integer userId, Integer bubbleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        Bubble bubble = bubbleRepository.findById(bubbleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bolha não encontrada"));

        Member member = new Member();
        member.setFkUser(user);
        member.setFkBubble(bubble);

        memberRepository.save(member);
    }
}