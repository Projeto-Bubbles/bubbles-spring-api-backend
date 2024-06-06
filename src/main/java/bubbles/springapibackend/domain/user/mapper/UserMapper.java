package bubbles.springapibackend.domain.user.mapper;

import bubbles.springapibackend.domain.user.User;
import bubbles.springapibackend.domain.user.dto.UserInfoDTO;
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
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setCpf(user.getCpf());
        userDTO.setNickname(user.getNickname());
        userDTO.setPassword(user.getPassword());
        userDTO.setImage(user.getImage());

        return userDTO;
    }

    public UserInfoDTO toUserInfoDTO(User user) {
        UserInfoDTO userDto = new UserInfoDTO();
        userDto.setIdUser(user.getIdUser());
        userDto.setUsername(user.getUsername());
        userDto.setNickname(user.getNickname());
        userDto.setEmail(user.getEmail());
        userDto.setImage(user.getImage());

        return userDto;
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
        user.setSecretKey(userDTO.getPassword());
        user.setImage(userDTO.getImage());

        return user;
    }
}
