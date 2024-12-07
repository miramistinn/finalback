package com.example.finall.services;

import com.example.finall.entity.Additional;
import com.example.finall.entity.BMenu;
import com.example.finall.entity.MainInformation;
import com.example.finall.entity.Menu;
import com.example.finall.repository.AddirionalServRep;
import com.example.finall.repository.AdditionalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CalculationService {
    private final MainInformationService mainInformationService;
    private final MenuService menuService;
    private final AdditionalService additionalService;

    public double calcuate(Long id) {
        return calcuateMenu(id) + calcuateAditional(id) + calcuateRoom(id);
    }

    public List<Double> takeList(Long id) {
        List<Double> list = new ArrayList<>();
        list.add(calcuateMenu(id));
        list.add(calcuateAditional(id));
        list.add(calcuateRoom(id));
        list.add(calcuate(id));
        return list;
    }

    public double calcuateRoom(Long id) {
        double cost = 0.0;
        if (mainInformationService.readById(id).getRoom() == 1) cost = 1000.0;
        if (mainInformationService.readById(id).getRoom() == 2) cost = 500.0;
        if (mainInformationService.readById(id).getRoom() == 3) cost = 450.0;
        return cost;
    }

    public double calcuateMenu(Long id) {
        Menu menu = menuService.readByMain(id);
        Double total = menu.getCost();
        return Math.round(total * 100.0) / 100.0;
    }

    public double calcuateAditional(Long id) {
        List<Additional> dop = additionalService.findByMenuId(id);
        Double total = 0.0;
        for (Additional add : dop) {
            total += add.getCost();
        }
        total = Math.round(total * 100.0) / 100.0;
        return total;
    }
}
