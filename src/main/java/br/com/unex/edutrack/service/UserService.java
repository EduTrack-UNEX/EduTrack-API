package br.com.unex.edutrack.service;

import br.com.unex.edutrack.dto.user.UserRequestDto;
import br.com.unex.edutrack.dto.user.UserResponseDto;
import br.com.unex.edutrack.mapper.user.UserMapper;
import br.com.unex.edutrack.model.User;
import br.com.unex.edutrack.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    UserService(UserRepository userRepository,UserMapper userMapper,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDto> listAllUSer(){
        return userRepository.findAll().stream().map(userMapper::toUserResponseDto).toList();
    }

    public UserResponseDto findUserById(int id){
        User user = userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Usuário não encontrado com o ID: " + id));
        return userMapper.toUserResponseDto(user);
    }

    public UserResponseDto saveUser(UserRequestDto data){

        User user = userMapper.toUser(data);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User persistedUser = userRepository.save(user);
        return userMapper.toUserResponseDto(persistedUser);
    }

    public UserResponseDto updateUser (UserRequestDto data,int id){
        User user = userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Usuario não encontrado com o ID: " + id));

        user.atualizarCom(data);

        if (data.password() != null && !data.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(data.password()));
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toUserResponseDto(updatedUser);
    }

    public void deleteUser(int id){
        if(!userRepository.existsById(id)){
            throw new EntityNotFoundException("Usuario nao encontrado com o ID: "+id);
        }
        userRepository.deleteById(id);
    }
}
