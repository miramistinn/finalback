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
@Table(name = "admin")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "fio")
    private String fio;
    @Column(name = "password", length = 1000)
    private String password;
    @Column(name = "active")
    private boolean active;
    @ElementCollection(targetClass = Roles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "admin_id"))
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles = new HashSet<>();


}
