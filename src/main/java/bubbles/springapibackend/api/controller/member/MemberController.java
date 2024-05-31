package bubbles.springapibackend.api.controller.member;

import bubbles.springapibackend.domain.bubble.dto.BubbleResponseDTO;
import bubbles.springapibackend.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Delete Member by User ID and Bubble ID",
            description = "Delete a member by its user ID and bubble ID.")
    @DeleteMapping("/{userId}/{bubbleId}")
    public ResponseEntity<Void> deleteMemberById(
            @Parameter(description = "User ID") @PathVariable Integer userId,
            @Parameter(description = "Bubble ID") @PathVariable Integer bubbleId) {
        boolean deleted = memberService.deleteMemberByUserIdAndBubbleId(userId, bubbleId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}