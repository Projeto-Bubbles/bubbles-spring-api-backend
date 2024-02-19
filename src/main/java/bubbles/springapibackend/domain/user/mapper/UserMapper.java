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
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setCpf(user.getCpf());
        userDTO.setNickname(user.getNickname());
        userDTO.setParole(user.getParole());

        return userDTO;
    }

    public User toEntity(UserDTO userDTO){
        if (userDTO == null) {
            throw new EntityNotFoundException("Usuário nulo");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setCpf(userDTO.getCpf());
        user.setNickname(userDTO.getNickname());
        user.setParole(userDTO.getParole());

        return user;
    }
}
