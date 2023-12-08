package bubbles.springapibackend.service.authorization;

import bubbles.springapibackend.api.configuration.security.TokenService;
import bubbles.springapibackend.domain.user.User;
import bubbles.springapibackend.domain.user.repository.UserModelRepository;
import bubbles.springapibackend.domain.user.repository.UserRepository;
import bubbles.springapibackend.service.user.dto.AuthetinticationDto;
import bubbles.springapibackend.service.user.dto.LoginResponseDto;
import bubbles.springapibackend.service.user.dto.RegisterDto;
import bubbles.springapibackend.domain.user.model.UserModel;
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
    private UserModelRepository userModelRepository;

    @Autowired
    private TokenService tokenService;

    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userModelRepository.findByEmail(email);
    }

    public ResponseEntity<Object> login(@RequestBody @Valid AuthetinticationDto data) {
        authenticationManager = context.getBean(AuthenticationManager.class);

        System.out.println(data.email());

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserModel) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }



    public ResponseEntity<Object> register(@RequestBody RegisterDto registerDto) {
<<<<<<< HEAD
        if (this.userRepository.existsByEmail(registerDto.email())) {
=======
        if (this.userModelRepository.existsByEmail(registerDto.email())) {
>>>>>>> 4a8918056083784ef93bde4f6832f1558d70d315
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());

        UserModel newUser = new UserModel(registerDto.email(), encryptedPassword);
        newUser.setCreatedAt(new Date(System.currentTimeMillis()));
        this.userModelRepository.save(newUser);
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
