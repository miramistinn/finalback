package com.example.finall.controllers;

import com.example.finall.dto.CategoryDTO;
import com.example.finall.dto.DishDTO;
import com.example.finall.entity.Admin;
import com.example.finall.entity.BMenu;
import com.example.finall.entity.Dish;
import com.example.finall.services.DishService;
import com.example.finall.services.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@AllArgsConstructor
public class MenuController {
    private final MenuService menuService;
    private final DishService dishService;

    @GetMapping("menu/dish")
    public ResponseEntity<List<Dish>> readAllDishes() {
        return new ResponseEntity<>(dishService.readAll(), HttpStatus.OK);
    }
    @GetMapping("menu/check/{id}")
    public ResponseEntity<List<Dish>> readAllDishes(@PathVariable  Long id) {
        return new ResponseEntity<>(dishService.readAll(), HttpStatus.OK);
    }

    @PostMapping("menu/check")
    public ResponseEntity<List<Dish>> readDishCat(@RequestBody CategoryDTO dto) {
        System.out.println(dto.getCategory());
        return new ResponseEntity<>(dishService.readByCateg(dto.getCategory()), HttpStatus.OK);
    }
    @PostMapping("menu/add/{id}")
    public HttpStatus add(@PathVariable  Long id, @RequestBody DishDTO dto) {
        System.out.println(dto);
        menuService.addDish(dto, id);
        return  HttpStatus.OK;
    }
    @GetMapping("menu/read/{id}")
    public ResponseEntity<List<BMenu>> readAllBmenu(@PathVariable  Long id) {
        System.out.println("пришло на сервер");
        return new ResponseEntity<>(menuService.readAllByMainId(id), HttpStatus.OK);
    }
    @DeleteMapping("menu/delete/{id}")
    public HttpStatus delete(@PathVariable  Long id) {
        System.out.println("пришло на сервер");
        menuService.deleteBMenu(id);
        return  HttpStatus.OK;    }
}
