package com.example.finall.repository;

import com.example.finall.entity.Client;
import com.example.finall.entity.MainInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainInformationRepository extends JpaRepository<MainInformation, Long> {

}
