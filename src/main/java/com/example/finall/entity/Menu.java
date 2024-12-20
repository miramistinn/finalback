package com.example.finall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Table(name = "menu")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "maininformation_id")
    private MainInformation mainInformation;

    @OneToMany
    @JoinColumn(name = "bmenu_id")
    private List<BMenu> bmenu;

    @Column(name = "cost")
    private Double cost;
}
