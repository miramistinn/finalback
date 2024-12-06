package com.example.finall.services;

import com.example.finall.dto.MainInformationDTO;
import com.example.finall.dto.RejectedDTO;
import com.example.finall.dto.UserDTO;
import com.example.finall.entity.Client;
import com.example.finall.entity.MainInformation;
import com.example.finall.entity.User;
import com.example.finall.repository.AdminRepository;
import com.example.finall.repository.MainInformationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MainInformationService {
    private final MainInformationRepository mainInformationRepository;
    private final UserService userService;

    public List<MainInformation> readAllnew() {
        List<MainInformation> mainInformations = mainInformationRepository.findAll();
        return mainInformations.stream()
                .filter(app -> app.getStatus().equals("new"))
                .collect(Collectors.toList());
    }
    public MainInformation readById(Long id) {
        return mainInformationRepository.findById(id).orElseThrow();
    }
    public void update(Long id,MainInformationDTO dto) {
        MainInformation mainInformation = readById(id);
        mainInformation.setStatus("proccesing");
        mainInformation.setSurname(dto.getSurname());
        mainInformation.setName(dto.getFirstName());
        mainInformation.setPatronymic(dto.getPatronymic());
        mainInformation.setQuantity(dto.getQuantity());
        mainInformation.setDateOfCreated(dto.getDate());
        mainInformation.setPhone(dto.getPhone());
        mainInformation.setRoom(dto.getRoom());
        mainInformation.setComments(dto.getComments());
        mainInformationRepository.save(mainInformation);
    }


    public MainInformation createApl(MainInformationDTO dto) {
        // Проверяем, что DTO не null
        if (dto == null) {
            throw new IllegalArgumentException("MainInformationDTO cannot be null.");
        }
        System.out.println("userService.clientHadApp();");
        // Проверяем наличие клиента
        userService.clientHadApp(); // Проверка, что клиент существует
        System.out.println("User user = User.getInstance()");
        User user = User.getInstance();
        MainInformation mainInformation;
        System.out.println("user.getEmail()==\"\"");
        if(user.getEmail()=="") {
             mainInformation = MainInformation.builder()
                    .name(dto.getFirstName())
                    .surname(dto.getSurname())
                    .patronymic(dto.getPatronymic())
                    .phone(dto.getPhone())
                    .comments(dto.getComments())
                    .room(dto.getRoom())
                    .status("new")
                    .dateOfCreated(dto.getDate())
                    .quantity(dto.getQuantity())
                    .build();
        }else {
            System.out.println("Client client");
            Client client = userService.readByEmail(user.getEmail());
            if (client == null) {
                mainInformation = MainInformation.builder()
                        .name(dto.getFirstName())
                        .surname(dto.getSurname())
                        .patronymic(dto.getPatronymic())
                        .phone(dto.getPhone())
                        .comments(dto.getComments())
                        .room(dto.getRoom())
                        .status("new")
                        .dateOfCreated(dto.getDate())
                        .quantity(dto.getQuantity())
                        .build();
            }else {
                System.out.println("Client client");
                mainInformation = MainInformation.builder()
                        .name(dto.getFirstName())
                        .surname(dto.getSurname())
                        .patronymic(dto.getPatronymic())
                        .phone(dto.getPhone())
                        .comments(dto.getComments())
                        .room(dto.getRoom())
                        .status("new")
                        .dateOfCreated(dto.getDate())
                        .quantity(dto.getQuantity())
                        .client(client)
                        .build();
            }
        }

        return mainInformationRepository.save(mainInformation);
    }

    public void reject(RejectedDTO dto) {
        MainInformation mainInformation = readById(dto.getMainId());
        mainInformation.setStatus("rejected");
        mainInformationRepository.save(mainInformation);
    }
    public void close(Long id) {
        MainInformation mainInformation = readById(id);
        mainInformation.setStatus("close");
        mainInformationRepository.save(mainInformation);
    }
    public  void createDogovor(Long id){

    }

}
