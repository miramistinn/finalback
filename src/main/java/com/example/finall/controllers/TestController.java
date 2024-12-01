package com.example.finall.controllers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test/")
@AllArgsConstructor
@CrossOrigin(origins = "*")

public class TestController {

    private static final Gson gson = new Gson();

    @GetMapping("/welcome")
    public ResponseEntity<?> welcome() {
        return ResponseEntity.ok(gson.toJson("adasdas"));
    }
    @GetMapping("/")
    public String welcom() { return "This is unprotected page"; }
    @CrossOrigin(origins = "*")
    @GetMapping("/users")
    public ResponseEntity<?> users() {
        return ResponseEntity.ok(gson.toJson("adasdas"));
    }
    @GetMapping("/admins")


    public String pageForAdmins() {
        return "This is page for only admins";
    }
    @GetMapping("/all")
    public String pageForAll () {
        return "This is page for all employees";
    }
}
