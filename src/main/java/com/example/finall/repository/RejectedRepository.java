package com.example.finall.repository;

import com.example.finall.entity.Rejected;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RejectedRepository extends JpaRepository<Rejected, Long> {

}
