package com.example.finall.services;

import com.example.finall.dto.RejectedDTO;
import com.example.finall.entity.MainInformation;
import com.example.finall.entity.Rejected;
import com.example.finall.repository.RejectedRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RejectService {
    private RejectedRepository rejectedRepository;
    MainInformationService mainInformationService;

    public Rejected reject(RejectedDTO dto) {
        mainInformationService.reject(dto);
        MainInformation mainInformation = mainInformationService.readById(dto.getMainId());
        return rejectedRepository.save(Rejected.builder()
                .reason(dto.getReason())
                .mainInformation(mainInformation)
                .build());
    }

    public List<Rejected> readAllRejected() {
        return rejectedRepository.findAll();
    }
}
