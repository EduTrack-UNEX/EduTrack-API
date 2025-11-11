package br.com.unex.edutrack.controller;

import br.com.unex.edutrack.dto.ApiResponse;
import br.com.unex.edutrack.dto.user.user.UserRequestDto;
import br.com.unex.edutrack.dto.user.user.UserResponseDto;
import br.com.unex.edutrack.service.UserService;
import br.com.unex.edutrack.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@Valid @RequestBody UserRequestDto request){
        UserResponseDto user = userService.saveUser(request);
        return ResponseUtil.created("Usuario Criado com sucesso",user);
    }

}
