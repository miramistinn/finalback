package com.example.finall.services;

import com.example.finall.entity.Client;
import com.example.finall.entity.Dish;
import com.example.finall.repository.DishRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DishService {
    private  final DishRepository dishRepository;

    public List<Dish> readAll() {
        return dishRepository.findAll();
    }
    public List<Dish> readByCateg(String categ) {
        return  dishRepository.findByCategory(categ);
    }
    public Dish readByName(String name) {
        return  dishRepository.findByName(name);
    }

}
