package com.example.finall.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MainInformationDTO {
private String firstName;
    private String surname;
    private String patronymic;
    private String phone;
    private String comments;
    private int room;
    private LocalDateTime date;
    private int  quantity;
}
