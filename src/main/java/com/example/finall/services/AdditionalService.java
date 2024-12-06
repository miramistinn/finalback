package com.example.finall.services;

import com.example.finall.dto.AddtiotionalDTO;
import com.example.finall.entity.Additional;
import com.example.finall.entity.AdditionalServiceEntity;
import com.example.finall.entity.MainInformation;
import com.example.finall.repository.AddirionalServRep;
import com.example.finall.repository.AdditionalRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.math3.analysis.function.Add;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class AdditionalService {
    private final AdditionalRepository additionalRepository;
    private final AddirionalServRep addirionalServRep;
    private final MainInformationService mainInformationService;

    public List<AdditionalServiceEntity> readAll() {
        return addirionalServRep.findAll();
    }

    public List<Additional> findByMenuId(Long id) {
        return additionalRepository.findByMainInformation_Id(id);
    }

    public AdditionalServiceEntity readById(Long id) {
        return addirionalServRep.findById(id).orElseThrow();
    }
    public Additional readAdditional(Long id) {
        return additionalRepository.findById(id).orElseThrow();
    }

    //  return clientRepository.save(Client.builder()
//                .email(dto.getEmail())
//                .password(dto.getPassword())
//                .active(true)
//                .build());
    public Additional add(AddtiotionalDTO dto) {
        return additionalRepository.save(Additional.builder()
                .mainInformation(mainInformationService.readById(dto.getAppId()))
                .additionalService(readById(dto.getAddId()))
                        .cost(readById(dto.getAddId()).getCost())
                .build());
    }
//BMenu b = findById(id);
//        Menu menu = b.getMenu();
//        menu.setCost(menu.getCost()-b.getDish().getCost()* b.getQuantity());
//        menuRepository.save(menu);
//        bMenuRepository.deleteById(id);
    public void delete(AddtiotionalDTO dto) {
        Additional additional = readAdditional(dto.getAddId());
        additionalRepository.delete(additional);
    }

    public void delete(Long addid) {
        Additional additional = readAdditional(addid);
        additionalRepository.delete(additional);
    }
}
