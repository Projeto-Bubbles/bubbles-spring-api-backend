package bubbles.springapibackend.api.controller.bubble;

import bubbles.springapibackend.api.enums.Category;
import bubbles.springapibackend.domain.bubble.Bubble;
import bubbles.springapibackend.domain.bubble.dto.BubbleRequestDTO;
import bubbles.springapibackend.domain.bubble.dto.BubbleResponseDTO;
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
    public ResponseEntity<List<BubbleResponseDTO>> getAllBubbles() {
        List<BubbleResponseDTO> bubbles = bubbleService.getAllBubbles();

        if (bubbles.isEmpty()) return ResponseEntity.noContent().build();

        List<BubbleResponseDTO> bubbleDTOS = bubbles.stream()
                .sorted(Comparator.comparing(BubbleResponseDTO::getIdBubble))
                .collect(Collectors.toList());
        return ResponseEntity.ok(bubbleDTOS);
    }

    @Operation(summary = "Pegar bolha pelo ID",
            description = "Retorna uma bolha pelo seu ID.")
    @GetMapping("/{bubbleId}")
    public ResponseEntity<BubbleResponseDTO> getBubbleById(
            @Parameter(description = "ID da bolha.") @PathVariable Integer bubbleId) {
        Bubble bubble = bubbleService.getBubbleById(bubbleId);
        BubbleResponseDTO bubbleDTO = bubbleMapper.toDTO(bubble);
        return ResponseEntity.ok(bubbleDTO);
    }

    @Operation(summary = "Pegar bolhas por título",
            description = "Retorna todas as bolhas cujos títulos contenham a sequência de" +
                    " caracteres fornecida, ignorando diferenças de maiúsculas e minúsculas.")
    @GetMapping("/headline")
    public ResponseEntity<List<BubbleResponseDTO>> getAllBubblesByTitleContainsIgnoreCase(
            @Parameter(description = "Título da bolha.")
            @RequestParam String bubbleTitle) {
        List<BubbleResponseDTO> bubbles = bubbleService
                .getAllBubblesByTitleContainsIgnoreCase(bubbleTitle);

        if (bubbles.isEmpty()) return ResponseEntity.notFound().build();

        List<BubbleResponseDTO> bubbleDTOS = bubbles.stream()
                .sorted(Comparator.comparing(BubbleResponseDTO::getIdBubble))
                .collect(Collectors.toList());

        return ResponseEntity.ok(bubbleDTOS);
    }

    @Operation(summary = "Pegar bolhas criadas após uma data",
            description = "Retorna todas as bolhas criadas a partir de uma data específica.")
    @GetMapping("/date/after")
    public ResponseEntity<List<BubbleResponseDTO>> getAllBubblesByCreationDateAfter(
            @Parameter(description = "Data de criação da bolha.")
            @RequestParam LocalDate bubbleCreationDate) {
        List<BubbleResponseDTO> bubbles = bubbleService
                .getAllBubblesByCreationDateAfter(bubbleCreationDate);

        if (bubbles.isEmpty()) return ResponseEntity.notFound().build();

        List<BubbleResponseDTO> bubbleDTOS = bubbles.stream()
                .sorted(Comparator.comparing(BubbleResponseDTO::getIdBubble))
                .collect(Collectors.toList());

        return ResponseEntity.ok(bubbleDTOS);
    }

    @Operation(summary = "Pegar bolhas criadas antes de uma data",
            description = "Retorna todas as bolhas criadas antes de uma data específica.")
    @GetMapping("/date/before")
    public ResponseEntity<List<BubbleResponseDTO>> getAllBubblesByCreationDateBefore(
            @Parameter(description = "Data de criação da bolha.")
            @RequestParam LocalDate bubbleCreationDate) {
        List<BubbleResponseDTO> bubbles = bubbleService
                .getAllBubblesByCreationDateBefore(bubbleCreationDate);

        if (bubbles.isEmpty()) return ResponseEntity.notFound().build();

        List<BubbleResponseDTO> bubbleDTOS = bubbles.stream()
                .sorted(Comparator.comparing(BubbleResponseDTO::getIdBubble))
                .collect(Collectors.toList());

        return ResponseEntity.ok(bubbleDTOS);
    }

    @Operation(summary = "Pegar bolhas por categoria",
            description = "Retorna todas as bolhas indicadas por uma lista de categorias.")
    @GetMapping("/categories")
    public ResponseEntity<List<BubbleResponseDTO>> getAllBubblesByCategory(
            @Parameter(description = "Categorias de bolhas.")
            @RequestParam List<String> bubbleCategories) {
        List<Category> categoryEnums = bubbleCategories.stream().map(Category::valueOf)
                .collect(Collectors.toList());
        List<BubbleResponseDTO> bubbles = bubbleService.getAllBubblesByCategory(categoryEnums);

        if (bubbles.isEmpty()) return getAllBubbles();

        List<BubbleResponseDTO> bubbleDTOS = bubbles.stream()
                .sorted(Comparator.comparing(BubbleResponseDTO::getIdBubble))
                .collect(Collectors.toList());

        return ResponseEntity.ok(bubbleDTOS);
    }

    @Operation(summary = "Pegar bolhas criadas por um usuário (ID)",
            description = "Retorna todas as bolhas criadas por um usuário específico," +
                    " usando seu ID.")
    @GetMapping("/creator/{userId}")
    public ResponseEntity<List<BubbleResponseDTO>> getAllBubblesByUserID(
            @Parameter(description = "ID do criador da bolha.")
            @PathVariable Integer userId) {
        List<BubbleResponseDTO> bubbles = bubbleService.getAllBubblesByUserId(userId);

        if (bubbles.isEmpty()) return ResponseEntity.notFound().build();

        List<BubbleResponseDTO> bubbleDTOS = bubbles.stream()
                .sorted(Comparator.comparing(BubbleResponseDTO::getIdBubble))
                .collect(Collectors.toList());

        return ResponseEntity.ok(bubbleDTOS);
    }

    @Operation(summary = "Pegar bolhas criadas por um usuário (Apelido)",
            description = "Retorna todas as bolhas criadas por um usuário específico," +
                    " usando seu apelido.")
    @GetMapping("/creator/nickname")
    public ResponseEntity<List<BubbleResponseDTO>> getAllBubblesByUserNickname(
            @Parameter(description = "Apelido do criador da bolha.")
            @RequestParam String userNickname) {
        List<BubbleResponseDTO> bubbles = bubbleService.getAllBubblesByUserNickname(userNickname);

        if (bubbles.isEmpty()) return ResponseEntity.notFound().build();

        List<BubbleResponseDTO> bubbleDTOS = bubbles.stream()
                .sorted(Comparator.comparing(BubbleResponseDTO::getIdBubble))
                .collect(Collectors.toList());

        return ResponseEntity.ok(bubbleDTOS);
    }

    @Operation(summary = "Criar bolha", description = "Cria uma nova bolha.")
    @PostMapping("/create")
    public ResponseEntity<BubbleResponseDTO> createNewBubble(
            @Validated @RequestBody BubbleRequestDTO newBubbleDTO) {
        BubbleResponseDTO newBubble = bubbleService.createNewBubble(newBubbleDTO);
        return new ResponseEntity<>(newBubble, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar bolha",
            description = "Atualiza uma bolha existente pelo seu ID.")
    @PatchMapping("/update/{bubbleId}")
    public ResponseEntity<BubbleResponseDTO> updateBubbleById(
            @Parameter(description = "ID da bolha.") @PathVariable Integer bubbleId,
            @Parameter(description = "JSON da bolha a ser atualizada.")
            @RequestBody BubbleResponseDTO updatedBubbleDTO) {
        BubbleResponseDTO updatedBubble = bubbleService.updateBubbleById(bubbleId, updatedBubbleDTO);
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
