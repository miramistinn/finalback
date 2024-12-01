package com.example.finall.services;

import com.example.finall.dto.UserDTO;
import com.example.finall.entity.Admin;
import com.example.finall.entity.Client;
import com.example.finall.entity.User;
import com.example.finall.repository.AdminRepository;
import com.example.finall.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {
    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;

    public Admin createAdmin(UserDTO dto) {
        System.out.println(dto.getEmail() + "   " + dto.getPassword());
        return adminRepository.save(Admin.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .fio(dto.getFio())
                .active(true)
                .build());

    }

    public Client createClient(UserDTO dto) {
        System.out.println(dto.getEmail() + "   " + dto.getPassword());
        return clientRepository.save(Client.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .active(true)
                .build());

    }

    //user part
    public Long checkIfExistClient(UserDTO dto) {
        if (clientRepository.findByEmail(dto.getEmail()) == null)
            return null;
        else return clientRepository.findByEmail(dto.getEmail()).getId();
    }

    public List<Client> readAll() {
        return clientRepository.findAll();
    }

    public Client readById(Long id) {
        return clientRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Client is not found - " + id));
    }

    public Client readByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public void clientHadApp() {
        User user = User.getInstance();

        // Находим клиента по email
        Client client = clientRepository.findByEmail(user.getEmail());

        // Проверяем, найден ли клиент
        if (client == null) {
            // Логируем и обрабатываем ситуацию
            return;
        }
        client.setHaveEvent(true);
        clientRepository.save(client);
    }

    public Long checkIfBun(UserDTO dto) {
        Client client = clientRepository.findByEmail(dto.getEmail());
        if (client != null && client.isActive()) {
            return client.getId();
        }

        Admin admin = adminRepository.findByEmail(dto.getEmail());
        if (admin != null && admin.isActive()) {
            return admin.getId();
        }

        return null;
    }

    public boolean haveBuy(String email) {
        return clientRepository.findByEmail(email).isHaveEvent();
    }

    //admin part
    public void clientBan(String email) {
        System.out.println(email);
        String modifiedStr = email.substring(1, email.length() - 1);
        System.out.println(modifiedStr);
        Client client = clientRepository.findByEmail(modifiedStr);

        if (client != null) {
            boolean isActive = client.isActive();
            client.setActive(!isActive);
            clientRepository.save(client);
            System.out.println(modifiedStr + " был изменен статус");
        } else {
            System.out.println("Клиент не найден");
        }
    }

    public void adminBan(String email) {
        System.out.println(email);
        String modifiedStr = email.substring(1, email.length() - 1);
        System.out.println(modifiedStr);
        Admin admin = adminRepository.findByEmail(modifiedStr);
        if (admin != null && !Objects.equals(admin.getEmail(), User.getInstance().getEmail())) {
            boolean isActive = admin.isActive();
            admin.setActive(!isActive);
            adminRepository.save(admin);
            System.out.println(modifiedStr + " был изменен статус");
        } else {
            System.out.println("Беда");
        }
    }

    public Long checkIfExistAdmin(UserDTO dto) {
        if (adminRepository.findByEmail(dto.getEmail()) == null)
            return null;
        else return adminRepository.findByEmail(dto.getEmail()).getId();
    }

    public List<Admin> readAllAdmin() {
        return adminRepository.findAll();
    }

    public Admin updateAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public void delete(Long id) {
        adminRepository.deleteById(id);
    }

    public Admin readByEmailAdmin(String email) {
        return adminRepository.findByEmail(email);
    }
}
