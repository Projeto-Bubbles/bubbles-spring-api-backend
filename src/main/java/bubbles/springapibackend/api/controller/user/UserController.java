package bubbles.springapibackend.api.controller.user;

import bubbles.springapibackend.domain.user.User;
import bubbles.springapibackend.domain.user.dto.UserDTO;
import bubbles.springapibackend.domain.user.mapper.UserMapper;
import bubbles.springapibackend.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping()
    @Operation(summary = "Get Available Users", description = "Returns all users for the current date or in the future.")
    public ResponseEntity<List<UserDTO>> getAvailableUsers() {
        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) return ResponseEntity.notFound().build();

        List<UserDTO> usersDTOS = users.stream().map(userMapper::toDTO)
                .sorted(Comparator.comparing(UserDTO::getId)).collect(Collectors.toList());
        return ResponseEntity.ok(usersDTOS);
    }

    @Operation(summary = "Get User by ID", description = "Returns an user by its unique ID.")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @Parameter(description = "Unique user ID") @PathVariable Integer id) {
        User user = userService.getUserById(id);
        UserDTO userDTO = userMapper.toDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @Operation(summary = "Get User by Email", description = "Returns user by a specific email.")
    @GetMapping("/email")
    public ResponseEntity<UserDTO> getUserByEmail(
            @Parameter(description = "User email") @RequestParam String email) {
        User user = userService.getUserByEmail(email);

        if (user == null) return ResponseEntity.notFound().build();

        UserDTO userDTO = userMapper.toDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @Operation(summary = "Create User", description = "Create a new user.")
    @PostMapping()
    public ResponseEntity<UserDTO> registerUser(@Validated @RequestBody UserDTO newUserDTO) {
        User savedUser = userMapper.toEntity(newUserDTO);
        savedUser = userService.registerUser(savedUser);
        newUserDTO = userMapper.toDTO(savedUser);
        return new ResponseEntity<>(newUserDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Edit User", description = "Edit an existing user.")
    @PatchMapping("/edit/{id}")
    public ResponseEntity<UserDTO> editUser(
            @Parameter(description = "User ID") @PathVariable Integer id,
            @Parameter(description = "Patched user JSON") @Validated @RequestBody UserDTO updatedUserDTO) {
        if (userService.getUserById(id) == null) return ResponseEntity.notFound().build();

        User user = userMapper.toEntity(updatedUserDTO);
        user.setIdUser(id);
        user = userService.updateUser(user);
        updatedUserDTO = userMapper.toDTO(user);
        return ResponseEntity.ok(updatedUserDTO);
    }

    @Operation(summary = "Delete User by ID",
            description = "Delete an user by its unique ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(
            @Parameter(description = "User ID") @PathVariable Integer idUser) {
        if (userService.getUserById(idUser) == null) return ResponseEntity.notFound().build();

        userService.deleteUserById(idUser);
        return ResponseEntity.noContent().build();
    }
}
