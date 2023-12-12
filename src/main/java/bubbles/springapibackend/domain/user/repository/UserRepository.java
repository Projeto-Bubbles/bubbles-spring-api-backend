package bubbles.springapibackend.domain.user.repository;

import bubbles.springapibackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {
    UserDetails findByEmail(String email);
    Boolean existsByEmail(String email);
}
