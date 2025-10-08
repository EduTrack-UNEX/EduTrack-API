package br.com.unex.edutrack.mapper.user;

import br.com.unex.edutrack.dto.user.UserRequestDto;
import br.com.unex.edutrack.dto.user.UserResponseDto;
import br.com.unex.edutrack.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserRequestDto data) {
        return new User.UserBuilder()
                .comName(data.nome())
                .comEmail(data.email())
                .comPassword(data.password())
                .build();
    }

    public UserResponseDto toUserResponseDto(User data) {
        return new UserResponseDto(
                data.getId(),
                data.getNome(),
                data.getEmail()
        );
    }
}
