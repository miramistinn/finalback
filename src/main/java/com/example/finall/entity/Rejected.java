package com.example.finall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "rejected")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rejected {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @PrimaryKeyJoinColumn
    private MainInformation mainInformation;

    @Column(name = "reason")
    private String reason;
}
