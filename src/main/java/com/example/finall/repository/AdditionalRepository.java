package com.example.finall.repository;

import com.example.finall.entity.Additional;
import com.example.finall.entity.Admin;
import com.example.finall.entity.BMenu;
import com.example.finall.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdditionalRepository extends JpaRepository<Additional, Long> {
    List<Additional> findByMainInformation_Id(Long id);

    Additional findByMainInformation_IdAndAdditionalService_Id(Long mainid, Long addid);
}
