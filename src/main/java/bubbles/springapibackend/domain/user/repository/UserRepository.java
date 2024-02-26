package bubbles.springapibackend.domain.user.repository;

import bubbles.springapibackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String userName);

    User findByNickname(String userNickname);

    User findByEmail(String userEmail);

    User getUserByIdUser(Integer userId);
}
