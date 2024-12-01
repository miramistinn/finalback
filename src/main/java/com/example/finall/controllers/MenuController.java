package com.example.finall.controllers;

import com.example.finall.entity.Admin;
import com.example.finall.entity.Dish;
import com.example.finall.services.DishService;
import com.example.finall.services.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@AllArgsConstructor
public class MenuController {
    private final MenuService menuService;
    private final DishService dishService;
    @GetMapping("/menu/dish")
    public ResponseEntity<List<Dish>> readAllDishes() {
        return new ResponseEntity<>(dishService.readAll(), HttpStatus.OK);
    }
    @GetMapping("/menu/check/{id}")
    public ResponseEntity<List<Dish>> readAllDishes(@PathVariable  Long id) {
        return new ResponseEntity<>(dishService.readAll(), HttpStatus.OK);
    }
}
