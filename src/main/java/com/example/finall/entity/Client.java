package com.example.finall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Table(name = "client")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password", length = 1000)
    private String password;
    @Column(name = "haveEvent")
    private boolean haveEvent;
    @Column(name = "active")
    private boolean active;
    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "client_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles = new HashSet<>();

}
