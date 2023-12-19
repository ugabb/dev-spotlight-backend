package com.devspotlight.devspotlight.controllers;

import com.devspotlight.devspotlight.dto.UserDTO;
import com.devspotlight.devspotlight.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO request) {
        Optional<UserDTO> response = userService.createUser(request);

        return response.map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));

    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @GetMapping("{userId}")
    public ResponseEntity<UserDTO> getUserByIdController(@PathVariable Long userId){
        Optional<UserDTO> userDTO = userService.getUserById(userId);
        return userDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        Optional<UserDTO> userDTO = userService.findByUsername(username);
        return userDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>( HttpStatus.NOT_FOUND));
    }



    @GetMapping("/user")
    public Map<String, Object> getUser(@AuthenticationPrincipal OAuth2User oAuth2User){
        return oAuth2User.getAttributes();
    }
}
