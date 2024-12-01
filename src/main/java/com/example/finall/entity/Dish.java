package com.example.finall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "dish")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "category")
    private String category;
    @Column(name = "name")
    private String name;
    @Column(name = "weight")
    private String weight;
    @Column(name = "cost")
    private Double cost;

}
