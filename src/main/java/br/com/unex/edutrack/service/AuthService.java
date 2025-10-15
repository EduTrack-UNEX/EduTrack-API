package br.com.unex.edutrack.service;

import br.com.unex.edutrack.dto.user.user.AuthRequestDto;
import br.com.unex.edutrack.dto.user.user.AuthResponseDto;
import br.com.unex.edutrack.exception.EmptyFieldException;
import br.com.unex.edutrack.model.User;
import br.com.unex.edutrack.repository.UserRepository;
import br.com.unex.edutrack.security.TokenService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthService(TokenService tokenService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public AuthResponseDto authenticateUser(AuthRequestDto data) {
        if (data.email() == null || data.email().isBlank() ||
                data.password() == null || data.password().isBlank()) {
            throw new EmptyFieldException("Campos de email ou senha são obrigatórios");
        }

        Optional<User> userOptional = userRepository.findByEmail(data.email());
        if (userOptional.isEmpty()) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(data.password(), user.getPassword())) {
            throw new BadCredentialsException("Credenciais inválidas");
        }

        String token = tokenService.generateToken(user);

        return new AuthResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                token,
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

}
