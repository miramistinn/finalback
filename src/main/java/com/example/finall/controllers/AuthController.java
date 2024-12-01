package com.example.finall.controllers;

import com.example.finall.dto.UserDTO;
import com.example.finall.entity.User;
import com.example.finall.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/log/create")
    public ResponseEntity<?> create(@RequestBody UserDTO dto) {
        System.out.println(dto.getEmail()+dto.getPassword());
        if (userService.checkIfExistClient(dto) == null && userService.checkIfExistAdmin(dto) == null) {
            User.getInstance().setAdmin(false);
            User.getInstance().setEmail(dto.getEmail());
            return new ResponseEntity<>(userService.createClient(dto), HttpStatus.OK);
        } else {
            String errorMessage = "Пользователь уже существует";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }

    }



    @PostMapping("/log/logIn")
    public ResponseEntity<?> logIn(@RequestBody UserDTO dto) {
        if (userService.checkIfExistClient(dto) != null) {
            User user = User.getInstance();
            user.setEmail(dto.getEmail());
            user.setAdmin(false);
            return ResponseEntity.ok("client");
        }
        if (userService.checkIfExistAdmin(dto) != null) {
            User user = User.getInstance();
            user.setEmail(dto.getEmail());
            user.setAdmin(true);
            return ResponseEntity.ok("admin");
        } else {
            String errorMessage = "Пользователя не существует";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }

    @PostMapping("/logOut")
    public HttpStatus logOut() {
        System.out.println("пользователь вышел из аккаунта");
        User.getInstance().setEmail("");
        User.getInstance().setAdmin(false);
        return HttpStatus.OK;
    }
}
