package bubbles.springapibackend.service.authorization;

import bubbles.springapibackend.api.configuration.security.TokenService;
import bubbles.springapibackend.domain.user.User;
import bubbles.springapibackend.domain.user.repository.UserRepository;
import bubbles.springapibackend.service.user.dto.AuthetinticationDto;
import bubbles.springapibackend.service.user.dto.LoginResponseDto;
import bubbles.springapibackend.service.user.dto.RegisterDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.Objects;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<Object> login(@RequestBody @Valid AuthetinticationDto data) {
        authenticationManager = context.getBean(AuthenticationManager.class);

        System.out.println(data.email());

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }



    public ResponseEntity<Object> register(@RequestBody RegisterDto registerDto) {
        if (this.userRepository.existsByEmail(registerDto.email())) {
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());

        User newUser = new User(registerDto.email(), encryptedPassword);
        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Object> validate(String token) {
        String tokenValidated = tokenService.validateToken(token);
        if (!Objects.equals(tokenValidated, "")) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
