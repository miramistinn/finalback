package com.example.finall.controllers;

import com.example.finall.dto.MainInformationDTO;
import com.example.finall.dto.UserDTO;
import com.example.finall.entity.Admin;
import com.example.finall.entity.Client;
import com.example.finall.entity.MainInformation;
import com.example.finall.services.MainInformationService;
import com.example.finall.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {
    private  final UserService userService;


    @PostMapping("/create/admin")
    public ResponseEntity<?> create(@RequestBody UserDTO dto){
        if (userService.checkIfExistClient(dto) == null && userService.checkIfExistAdmin(dto) == null) {
            return new ResponseEntity<>(userService.createAdmin(dto), HttpStatus.OK);
        }
        else {
            String errorMessage = "Пользователь уже существует";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
    }
    @GetMapping("/read/admin")
    public ResponseEntity<List<Admin>> readAllAdmin() {
        return new ResponseEntity<>(userService.readAllAdmin(), HttpStatus.OK);
    }
    @GetMapping("/read/client")
    public ResponseEntity<List<Client>> readAll() {
        return new ResponseEntity<>(userService.readAll(), HttpStatus.OK);
    }
    @PostMapping("/client/ban")
    public HttpStatus clientBun(@RequestBody String email){
        System.out.println(email);
        userService.clientBan(email);
        return  HttpStatus.OK;
    }
    @PostMapping("/admin/ban")
    public HttpStatus adminBun(@RequestBody String email){
        userService.adminBan(email);
        return  HttpStatus.OK;
    }
}

