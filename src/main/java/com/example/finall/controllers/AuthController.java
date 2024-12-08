package com.example.finall.controllers;

import com.example.finall.dto.UserDTO;
import com.example.finall.entity.MainInformation;
import com.example.finall.entity.User;
import com.example.finall.services.MainInformationService;
import com.example.finall.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@AllArgsConstructor
public class AuthController {
    private final UserService userService;
    private final MainInformationService mainInformationService;

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
    @PostMapping("/log/check")
    public ResponseEntity<?> logCheck() {
        try {
            User user = User.getInstance();
            System.out.println(user.getEmail());
            System.out.println("logCheck");

            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                return ResponseEntity.ok("no");
            }

            String role = userService.checkRole();
            System.out.println(role);
            if (Objects.equals(role, "admin")) {
                return ResponseEntity.ok("admin");
            } else if (userService.haveBuy(user.getEmail())) {
                return ResponseEntity.ok("client have");
            }
            return ResponseEntity.ok("client");

        } catch (Exception e) {
            // Логирование ошибки
            System.err.println("Ошибка при проверке логина: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка сервера: " + e.getMessage());
        }
    }
    @PostMapping("/log/auth")
    public ResponseEntity<?> log(@RequestBody UserDTO dto) {
        return getResponseEntity(dto);
    }
    @PostMapping("/log/readApp")
    public ResponseEntity<List<MainInformation>> readApp() {
        return new ResponseEntity<>(mainInformationService.readUserApp(), HttpStatus.OK);
    }

    private ResponseEntity<?> getResponseEntity(@RequestBody UserDTO dto) {
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


    @PostMapping("/log/logIn")
    public ResponseEntity<?> logIn(@RequestBody UserDTO dto) {
        return getResponseEntity(dto);
    }

    @PostMapping("/log/logOut")
    public HttpStatus logOut() {
        System.out.println("пользователь вышел из аккаунта");
        User.getInstance().setEmail("");
        User.getInstance().setAdmin(false);
        return HttpStatus.OK;
    }
}
