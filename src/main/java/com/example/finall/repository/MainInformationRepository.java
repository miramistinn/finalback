package com.example.finall.repository;

import com.example.finall.entity.Client;
import com.example.finall.entity.MainInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainInformationRepository extends JpaRepository<MainInformation, Long> {
List<MainInformation> readByClient_Email(String email);
}
