package bubbles.springapibackend.domain.user.repository;

import bubbles.springapibackend.domain.user.User;
import bubbles.springapibackend.domain.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface UserModelRepository extends JpaRepository<UserModel, UUID> {
    UserDetails findByEmail(String email);
    Boolean existsByEmail(String email);

}
