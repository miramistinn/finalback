package com.example.finall.repository;

import com.example.finall.entity.Client;
import com.example.finall.entity.Dish;
import com.example.finall.entity.MainInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    List<Dish> findByCategory(String category);
    Dish findByName(String name);

}
