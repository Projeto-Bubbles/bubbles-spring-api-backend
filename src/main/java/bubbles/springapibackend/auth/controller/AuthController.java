package bubbles.springapibackend.auth.controller;

import bubbles.springapibackend.auth.service.AuthorizationService;
import bubbles.springapibackend.user.dto.AuthetinticationDto;
import bubbles.springapibackend.user.dto.RegisterDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthetinticationDto authetinticationDto) {
        return authorizationService.login(authetinticationDto);
    }

    @PostMapping("/validate")
    public ResponseEntity<Object> validate(@RequestParam(required = true) String token) {
        return authorizationService.validate(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDto registerDto) {
        return authorizationService.register(registerDto);
    }
}
