package br.com.unex.edutrack.controller;

import br.com.unex.edutrack.dto.ApiResponse;
import br.com.unex.edutrack.dto.user.user.AuthRequestDto;
import br.com.unex.edutrack.dto.user.user.AuthResponseDto;
import br.com.unex.edutrack.service.AuthService;
import br.com.unex.edutrack.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RequestMapping("/v1/users/login")
@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AuthResponseDto>> login (@RequestBody AuthRequestDto request) throws AuthenticationException {
        AuthResponseDto user = authService.authenticateUser(request);
        return ResponseUtil.ok("Usuario Logado com Sucesso",user);
    }
}
