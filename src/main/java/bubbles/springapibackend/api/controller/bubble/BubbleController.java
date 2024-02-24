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

import java.time.LocalDate;
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

    @Operation(summary = "Pegar todas as bolhas",
            description = "Retorna todas as bolhas disponíveis.")
    @GetMapping()
    public ResponseEntity<List<BubbleDTO>> getAllBubbles() {
        List<BubbleDTO> bubbles = bubbleService.getAllBubbles();

        if (bubbles.isEmpty()) return ResponseEntity.noContent().build();

        List<BubbleDTO> bubbleDTOS = bubbles.stream()
                .sorted(Comparator.comparing(BubbleDTO::getId))
                .collect(Collectors.toList());
        return ResponseEntity.ok(bubbleDTOS);
    }

    @Operation(summary = "Pegar bolha pelo ID",
            description = "Retorna uma bolha pelo seu ID.")
    @GetMapping("/{bubbleId}")
    public ResponseEntity<BubbleDTO> getBubbleById(
            @Parameter(description = "ID da bolha.") @PathVariable Integer bubbleId) {
        Bubble bubble = bubbleService.getBubbleById(bubbleId);
        BubbleDTO bubbleDTO = bubbleMapper.toDTO(bubble);
        return ResponseEntity.ok(bubbleDTO);
    }

    @Operation(summary = "Pegar bolhas por título",
            description = "Retorna todas as bolhas cujos títulos contenham a sequência de" +
                    " caracteres fornecida, ignorando diferenças de maiúsculas e minúsculas.")
    @GetMapping("/headline")
    public ResponseEntity<List<BubbleDTO>> getAllBubblesByTitleContainsIgnoreCase(
            @Parameter(description = "Título da bolha.")
            @RequestParam String bubbleTitle) {
        List<BubbleDTO> bubbles = bubbleService
                .getAllBubblesByTitleContainsIgnoreCase(bubbleTitle);

        if (bubbles.isEmpty()) return ResponseEntity.notFound().build();

        List<BubbleDTO> bubbleDTOS = bubbles.stream()
                .sorted(Comparator.comparing(BubbleDTO::getId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(bubbleDTOS);
    }

    @Operation(summary = "Pegar bolhas criadas após uma data",
            description = "Retorna todas as bolhas criadas a partir de uma data específica.")
    @GetMapping("/date/after")
    public ResponseEntity<List<BubbleDTO>> getAllBubblesByCreationDateAfter(
            @Parameter(description = "Data de criação da bolha.")
            @RequestParam LocalDate bubbleCreationDate) {
        List<BubbleDTO> bubbles = bubbleService
                .getAllBubblesByCreationDateAfter(bubbleCreationDate);

        if (bubbles.isEmpty()) return ResponseEntity.notFound().build();

        List<BubbleDTO> bubbleDTOS = bubbles.stream()
                .sorted(Comparator.comparing(BubbleDTO::getId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(bubbleDTOS);
    }

    @Operation(summary = "Pegar bolhas criadas antes de uma data",
            description = "Retorna todas as bolhas criadas antes de uma data específica.")
    @GetMapping("/date/before")
    public ResponseEntity<List<BubbleDTO>> getAllBubblesByCreationDateBefore(
            @Parameter(description = "Data de criação da bolha.")
            @RequestParam LocalDate bubbleCreationDate) {
        List<BubbleDTO> bubbles = bubbleService
                .getAllBubblesByCreationDateBefore(bubbleCreationDate);

        if (bubbles.isEmpty()) return ResponseEntity.notFound().build();

        List<BubbleDTO> bubbleDTOS = bubbles.stream()
                .sorted(Comparator.comparing(BubbleDTO::getId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(bubbleDTOS);
    }

    @Operation(summary = "Pegar bolhas por categoria",
            description = "Retorna todas as bolhas indicadas por uma lista de categorias.")
    @GetMapping("/categories")
    public ResponseEntity<List<BubbleDTO>> getAllBubblesByCategory(
            @Parameter(description = "Categorias de bolhas.")
            @RequestParam List<String> bubbleCategories) {
        List<Category> categoryEnums = bubbleCategories.stream().map(Category::valueOf)
                .collect(Collectors.toList());
        List<BubbleDTO> bubbles = bubbleService.getAllBubblesByCategory(categoryEnums);

        if (bubbles.isEmpty()) return ResponseEntity.notFound().build();

        List<BubbleDTO> bubbleDTOS = bubbles.stream()
                .sorted(Comparator.comparing(BubbleDTO::getId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(bubbleDTOS);
    }

    @Operation(summary = "Pegar bolhas criadas por um usuário (ID)",
            description = "Retorna todas as bolhas criadas por um usuário específico," +
                    " usando seu ID.")
    @GetMapping("/creator/{userId}")
    public ResponseEntity<List<BubbleDTO>> getAllBubblesByUserID(
            @Parameter(description = "ID do criador da bolha.")
            @PathVariable Integer userId) {
        List<BubbleDTO> bubbles = bubbleService.getAllBubblesByUserId(userId);

        if (bubbles.isEmpty()) return ResponseEntity.notFound().build();

        List<BubbleDTO> bubbleDTOS = bubbles.stream()
                .sorted(Comparator.comparing(BubbleDTO::getId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(bubbleDTOS);
    }

    @Operation(summary = "Pegar bolhas criadas por um usuário (Apelido)",
            description = "Retorna todas as bolhas criadas por um usuário específico," +
                    " usando seu apelido.")
    @GetMapping("/creator/nickname")
    public ResponseEntity<List<BubbleDTO>> getAllBubblesByUserNickname(
            @Parameter(description = "Apelido do criador da bolha.")
            @RequestParam String userNickname) {
        List<BubbleDTO> bubbles = bubbleService.getAllBubblesByUserNickname(userNickname);

        if (bubbles.isEmpty()) return ResponseEntity.notFound().build();

        List<BubbleDTO> bubbleDTOS = bubbles.stream()
                .sorted(Comparator.comparing(BubbleDTO::getId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(bubbleDTOS);
    }

    @Operation(summary = "Criar bolha",
            description = "Cria uma nova bolha.")
    @PostMapping("/create")
    public ResponseEntity<BubbleDTO> createNewBubble(
            @Validated @RequestBody BubbleDTO newBubbleDTO) {
        BubbleDTO newBubble = bubbleService.createNewBubble(newBubbleDTO);
        return new ResponseEntity<>(newBubble, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar bolha",
            description = "Atualiza uma bolha existente pelo seu ID.")
    @PatchMapping("/update/{bubbleId}")
    public ResponseEntity<BubbleDTO> updateBubbleById(
            @Parameter(description = "ID da bolha.") @PathVariable Integer bubbleId,
            @Parameter(description = "JSON da bolha a ser atualizada.")
            @RequestBody BubbleDTO updatedBubbleDTO) {
        BubbleDTO updatedBubble = bubbleService.updateBubbleById(bubbleId, updatedBubbleDTO);
        return ResponseEntity.ok().body(updatedBubble);
    }

    @Operation(summary = "Deletar Bolha",
            description = "Deleta uma bolha existente pelo seu ID")
    @DeleteMapping("/{bubbleId}")
    public ResponseEntity<Void> deleteBubbleById(
            @Parameter(description = "ID da bolha") @PathVariable Integer bubbleId) {
        if (bubbleService.getBubbleById(bubbleId) == null) return ResponseEntity.notFound()
                .build();

        bubbleService.deleteBubbleById(bubbleId);
        return ResponseEntity.noContent().build();
    }
}
