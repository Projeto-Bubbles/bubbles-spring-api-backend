package bubbles.springapibackend.domain.user.repository;

import bubbles.springapibackend.domain.user.User;
import bubbles.springapibackend.domain.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

<<<<<<< HEAD
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    UserDetails findByEmail(String email);
    Boolean existsByEmail(String email);
=======
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
>>>>>>> 4a8918056083784ef93bde4f6832f1558d70d315
}
