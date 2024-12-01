package com.example.finall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Table(name = "maininformation")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MainInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "status")
    private String status;
    @Column(name = "comments")
    private String comments;

    @Column(name = "phone")
    private String phone;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "room")
    private int room;
    @Column(name = "cost")
    private Double cost;
    @Column(name = "dateOfCreated")
    private LocalDateTime dateOfCreated;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

}
