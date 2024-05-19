package bubbles.springapibackend.api.controller;

import bubbles.springapibackend.domain.bubble.dto.BubbleResponseDTO;
import bubbles.springapibackend.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BubbleResponseDTO>> getBubblesForUser(@PathVariable Integer userId) {
        List<BubbleResponseDTO> bubbles = memberService.getBubblesForUser(userId);
        return ResponseEntity.ok(bubbles);
    }

    @PostMapping("/user/{userId}/bubble/{bubbleId}")
    public ResponseEntity<Void> addUserToBubble(@PathVariable Integer userId, @PathVariable Integer bubbleId) {
        memberService.addUserToBubble(userId, bubbleId);
        return ResponseEntity.ok().build();
    }
}