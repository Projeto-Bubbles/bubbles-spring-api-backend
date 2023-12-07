package bubbles.springapibackend.api.controller.bubble;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.bubble.repository.BubbleRepository;
import bubbles.springapibackend.service.bubble.BubbleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bubbles")
@RequiredArgsConstructor
public class BubbleController {
    private final BubbleRepository bubbleRepository;
    private final BubbleService bubbleService;

    @GetMapping()
    @Operation(summary = "Get Available Bubbles", description = "Returns all bubbles for the current date or in the future.")
    public ResponseEntity<List<Bubble>> getAvailableBubbles() {
        List<Bubble> bubbles = bubbleRepository.findAll();

        if (bubbles.isEmpty()) return ResponseEntity.noContent().build();

        Collections.sort(bubbles, Comparator.comparing(Bubble::getId));

        return ResponseEntity.ok(bubbles);
    }

    @Operation(summary = "Get Bubble by ID", description = "Returns an bubble by its unique ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Bubble>> getBubbleById(
            @Parameter(description = "Unique bubble ID") @PathVariable Integer id) {
        Optional<Bubble> bubbleOpt = bubbleRepository.findById(id);
        return ResponseEntity.of(Optional.ofNullable(bubbleOpt));
    }

    @Operation(summary = "Get Bubble by Creator", description = "Returns bubbles created by a specific user.")
    @GetMapping("/creator")
    public ResponseEntity<List<Bubble>> getBubblesByAuthor(
            @Parameter(description = "Creator's name") @RequestParam String creator) {
        List<Bubble> bubbles = bubbleRepository.findByCreatorName(creator);
        if (bubbles.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(bubbles);
    }

    @GetMapping("/filtered")
    @Operation(summary = "Get Bubbles by Category",
            description = "Returns bubbles associated with a specific category.")
    public ResponseEntity<List<Bubble>> getBubblesByCategory(
            @Parameter(description = "Bubble categories") @RequestParam List<String> categories) {
        List<Category> categoryEnums = categories.stream().map(Category::valueOf).collect(Collectors.toList());
        List<Bubble> bubbles = bubbleService.getFilteredBubbles(categoryEnums);

        if (bubbles.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(bubbles);
    }

    @Operation(summary = "Create Bubble", description = "Create a new bubble.")
    @PostMapping()
    public ResponseEntity<Bubble> createBubble(
            @Validated @RequestBody Bubble newBubble) {
        Bubble savedBubble = bubbleRepository.save(newBubble);
        return ResponseEntity.ok().body(savedBubble);
    }

    @Operation(summary = "Edit Bubble", description = "Edit an existing bubble.")
    @PatchMapping("/edit/{id}")
    public ResponseEntity<Bubble> editBubble(
            @Parameter(description = "Bubble ID") @PathVariable Integer id,
            @Parameter(description = "Patched bubble JSON") @Validated @RequestBody Bubble updatedBubble) {
        Optional<Bubble> existingBubbleOpt = bubbleRepository.findById(id);
        if (existingBubbleOpt.isPresent()) {
            Bubble existingBubble = existingBubbleOpt.get();
            existingBubble.setName(updatedBubble.getName());
            updatedBubble = bubbleRepository.save(existingBubble);
            return ResponseEntity.ok(updatedBubble);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete Bubble by ID",
            description = "Delete an bubble by its unique ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBubbleById(
            @Parameter(description = "Bubble ID") @PathVariable Integer id) {
        Optional<Bubble> existingBubbleOpt = bubbleRepository.findById(id);

        if (existingBubbleOpt.isPresent()) {
            bubbleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
