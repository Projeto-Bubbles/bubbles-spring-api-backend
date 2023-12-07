package bubbles.springapibackend.domain.user.repository;

import bubbles.springapibackend.domain.user.User;
import bubbles.springapibackend.domain.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
