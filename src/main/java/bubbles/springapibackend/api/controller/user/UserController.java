package bubbles.springapibackend.api.controller.user;

import bubbles.springapibackend.domain.user.User;
import bubbles.springapibackend.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("email")
    public ResponseEntity<User> getUserById(@RequestParam String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(user);
    }

    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User userSaved = userRepository.save(user);

        return ResponseEntity.created(null).body(user);
    }

}
