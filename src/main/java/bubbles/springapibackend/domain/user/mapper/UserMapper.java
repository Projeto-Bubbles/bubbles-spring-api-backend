package bubbles.springapibackend.domain.user.mapper;

import bubbles.springapibackend.domain.user.User;
import bubbles.springapibackend.domain.user.dto.UserDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        if (user == null) {
            throw new EntityNotFoundException("Usuário nulo");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getIdUser());
        userDTO.setName(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setCpf(user.getCpf());
        userDTO.setUsername(user.getNickname());
        userDTO.setPassword(user.getPassword());

        return userDTO;
    }

    public User toEntity(UserDTO userDTO){
        if (userDTO == null) {
            throw new EntityNotFoundException("Usuário nulo");
        }

        User user = new User();
        user.setUsername(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setCpf(userDTO.getCpf());
        user.setUsername(userDTO.getUsername());
        user.setSecretKey(userDTO.getPassword());

        return user;
    }
}
