package bubbles.springapibackend.api.controller.bubble;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.bubble.dto.BubbleDTO;
import bubbles.springapibackend.domain.bubble.mapper.BubbleMapper;
import bubbles.springapibackend.service.bubble.BubbleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bubbles")
public class BubbleController {
    private final BubbleService bubbleService;
    private final BubbleMapper bubbleMapper;

    @Autowired
    public BubbleController(BubbleService bubbleService, BubbleMapper bubbleMapper) {
        this.bubbleService = bubbleService;
        this.bubbleMapper = bubbleMapper;
    }

    @Operation(summary = "Get Available Bubbles", description = "Returns all bubbles for the current date or in the future.")
    @GetMapping()
    public ResponseEntity<List<BubbleDTO>> getAvailableBubbles() {
        List<BubbleDTO> bubbles = bubbleService.getAllBubbles();

        if (bubbles.isEmpty()) return ResponseEntity.notFound().build();

        List<BubbleDTO> bubbleDTOS = bubbles.stream()
                .sorted(Comparator.comparing(BubbleDTO::getId))
                .collect(Collectors.toList());
        return ResponseEntity.ok(bubbleDTOS);
    }

    @Operation(summary = "Get Bubble by ID", description = "Returns an bubble by its unique ID.")
    @GetMapping("/{id}")
    public ResponseEntity<BubbleDTO> getBubbleById(
            @Parameter(description = "Unique bubble ID") @PathVariable Integer id) {
        Bubble bubble = bubbleService.getBubbleById(id);
        BubbleDTO bubbleDTO = bubbleMapper.toDTO(bubble);
        return ResponseEntity.ok(bubbleDTO);
    }

    @Operation(summary = "Get Bubble by Creator",
            description = "Returns bubbles associated with a specific creator.")
    @GetMapping("/creator")
    public ResponseEntity<List<BubbleDTO>> getBubbleByCreator(
            @Parameter(description = "Bubble Creator") @RequestParam String creatorNickname) {
        List<BubbleDTO> bubbles = bubbleService.getBubbleByCreatorNickname(creatorNickname);

        if (bubbles.isEmpty()) return ResponseEntity.noContent().build();

        List<BubbleDTO> bubbleDTOS = bubbles.stream()
                .sorted(Comparator.comparing(BubbleDTO::getId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(bubbleDTOS);
    }

    @GetMapping("/filtered")
    @Operation(summary = "Get Bubbles by Category",
            description = "Returns bubbles associated with a specific category.")
    public ResponseEntity<List<BubbleDTO>> getBubblesByCategory(
            @Parameter(description = "Bubble categories") @RequestParam List<String> categories) {
        List<Category> categoryEnums = categories.stream().map(Category::valueOf).collect(Collectors.toList());
        List<BubbleDTO> bubbles = bubbleService.getFilteredBubbles(categoryEnums);

        if (bubbles.isEmpty()) return ResponseEntity.noContent().build();

        List<BubbleDTO> bubbleDTOS = bubbles.stream()
                .sorted(Comparator.comparing(BubbleDTO::getId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(bubbleDTOS);
    }

    @Operation(summary = "Create Bubble", description = "Create a new bubble.")
    @PostMapping()
    public ResponseEntity<BubbleDTO> createBubble(
            @Validated @RequestBody BubbleDTO newBubbleDTO) {
        BubbleDTO savedBubble = bubbleService.createBubble(newBubbleDTO);
        return new ResponseEntity<>(savedBubble, HttpStatus.CREATED);
    }

    @Operation(summary = "Edit Bubble", description = "Edit an existing bubble.")
    @PatchMapping("/edit/{bubbleId}")
    public ResponseEntity<BubbleDTO> editBubble(
            @Parameter(description = "Bubble ID") @PathVariable Integer bubbleId,
            @Parameter(description = "Patched bubble JSON") @RequestBody BubbleDTO updatedBubbleDTO) {
        BubbleDTO updatedBubble = bubbleService.updateBubble(bubbleId, updatedBubbleDTO);
        return ResponseEntity.ok().body(updatedBubble);
    }

    @Operation(summary = "Delete Bubble by ID",
            description = "Delete an bubble by its unique ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBubbleById(
            @Parameter(description = "Bubble ID") @PathVariable Integer id) {
        if (bubbleService.getBubbleById(id) == null) return ResponseEntity.notFound().build();

        bubbleService.deleteBubbleById(id);
        return ResponseEntity.noContent().build();
    }
}
