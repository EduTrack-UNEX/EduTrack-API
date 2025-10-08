package br.com.unex.edutrack.controller;

import br.com.unex.edutrack.dto.user.UserRequestDto;
import br.com.unex.edutrack.dto.user.UserResponseDto;
import br.com.unex.edutrack.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto request){
        UserResponseDto user = userService.saveUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable int id, @RequestBody @Valid UserRequestDto request){
        UserResponseDto updatedUser = userService.updateUser(request,id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserbyId(@PathVariable int id) {
        UserResponseDto user = userService.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUserAll(){
        List<UserResponseDto> users = userService.listAllUSer();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

}
