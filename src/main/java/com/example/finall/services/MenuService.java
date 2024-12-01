package com.example.finall.services;

import com.example.finall.entity.Client;
import com.example.finall.entity.Menu;
import com.example.finall.repository.MainInformationRepository;
import com.example.finall.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MenuService {
    private  final MenuRepository menuRepository;
    private  final MainInformationService mainInformationService;

    public Menu readByMain(Long id) {
        Menu menu = menuRepository.findByMainInformation_Id(id);
        // Если объект не найден, создаем новый
        if (menu == null) {
            menu = new Menu();
            menu.setMainInformation(mainInformationService.readById(id));
            menuRepository.save(menu);
        }
        return menu;
    }
}
