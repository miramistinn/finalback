package com.example.finall.repository;

import com.example.finall.entity.BMenu;
import com.example.finall.entity.Menu;
import com.example.finall.entity.Rejected;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BMenuRepository extends JpaRepository<BMenu, Long> {
    List<BMenu> findByMenu_Id(Long id);
}
