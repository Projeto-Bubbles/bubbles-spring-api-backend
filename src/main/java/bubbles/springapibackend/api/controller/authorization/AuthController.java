package bubbles.springapibackend.api.controller.authorization;

import bubbles.springapibackend.service.authorization.AuthorizationService;
import bubbles.springapibackend.service.authorization.dto.AuthetinticationDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    final AuthorizationService authorizationService;

    public AuthController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthetinticationDto authetinticationDto, HttpServletResponse response) {
        return authorizationService.login(authetinticationDto, response);
    }

    @PostMapping("/validate")
    public ResponseEntity<Object> validate(@RequestParam String token) {
        return authorizationService.validate(token);
    }
}
