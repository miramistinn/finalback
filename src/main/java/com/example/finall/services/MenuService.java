package com.example.finall.services;

import com.example.finall.dto.DishDTO;
import com.example.finall.entity.BMenu;
import com.example.finall.entity.Client;
import com.example.finall.entity.Dish;
import com.example.finall.entity.Menu;
import com.example.finall.repository.BMenuRepository;
import com.example.finall.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MenuService {
    private  final MenuRepository menuRepository;
    private  final MainInformationService mainInformationService;
    private  final BMenuRepository bMenuRepository;
    private final DishService dishService;

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
    public List<BMenu> findBMenuByMenuId(Long id){
        return bMenuRepository.findByMenu_Id(id);
    }
    public List<BMenu> readAllByMainId(Long MainId){
        Menu menu = readByMain(MainId);
        return findBMenuByMenuId(menu.getId());
    }
    public BMenu addDish(DishDTO dto, Long id){
        Dish dish = dishService.readByName(dto.getName());
        Menu menu = readByMain(id);
        Double cst = menu.getCost();
        if(cst==null) cst=0.0;
        menu.setCost(dish.getCost()* dto.getQuantity()+cst);
        return bMenuRepository.save(BMenu.builder()
                        .dish(dish)
                        .quantity(dto.getQuantity())
                        .menu(readByMain(id))
                .build());
    }
    public BMenu findById(Long id){
        return bMenuRepository.findById(id).orElseThrow(() ->
                new RuntimeException("it is not found - " + id));
    }

    public void deleteBMenu(Long id){
        BMenu b = findById(id);
        Menu menu = b.getMenu();
        menu.setCost(menu.getCost()-b.getDish().getCost()* b.getQuantity());
        menuRepository.save(menu);
        bMenuRepository.deleteById(id);
    }
}
