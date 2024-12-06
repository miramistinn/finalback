package com.example.finall.services;

import com.example.finall.entity.BMenu;
import com.example.finall.entity.Dish;
import com.example.finall.entity.MainInformation;
import lombok.AllArgsConstructor;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DogovorService {
    private final MainInformationService mainInformationService;
    private final MenuService menuService;

    public void create(Long id) {
        MainInformation mainInformation = mainInformationService.readById(id);
        String templatePath = "F:\\5 сем\\курсач\\договоры\\Dogovor.docx";
        String outputPath = "F:\\5 сем\\курсач\\договоры\\" + mainInformation.getId() + ".docx"; // Путь к выходному файлу

        String name = mainInformation.getName() + " " + mainInformation.getSurname() + " " + mainInformation.getPatronymic();
        List<BMenu> menuList = menuService.readAllByMainId(id);
        List<DishInfo> dishInfoList = new ArrayList<>();

        for (BMenu bMenu : menuList) {
            Dish dish = bMenu.getDish(); // Получаем объект Dish из BMenu
            if (dish != null) {
                DishInfo dishInfo = new DishInfo(dish.getName(), bMenu.getQuantity(), dish.getCost() * bMenu.getQuantity());
                dishInfoList.add(dishInfo);
            }
        }

        try (FileInputStream fis = new FileInputStream(templatePath);
             XWPFDocument document = new XWPFDocument(fis)) {
            System.out.println("Заполнение заполнителей");

            for (XWPFParagraph paragraph : document.getParagraphs()) {
                replacePlaceholder(paragraph, "{{name}}", name);
                replacePlaceholder(paragraph, "{{room}}", String.valueOf(mainInformation.getRoom()));
                replacePlaceholder(paragraph, "{{quantity}}", String.valueOf(mainInformation.getQuantity()));

                if (paragraph.getText().contains("{{insert}}")) {
                    insertTableAfterParagraph(paragraph, dishInfoList);
                    break; // Выход из цикла после вставки таблицы
                }
            }

            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                document.write(fos);
            }

            System.out.println("Документ успешно заполнен!");
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + templatePath);
            e.printStackTrace();
        } catch (NotOfficeXmlFileException e) {
            System.err.println("Ошибка: файл не является корректным OOXML: " + templatePath);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertTableAfterParagraph(XWPFParagraph paragraph, List<DishInfo> dishInfoList) {
        int pos = paragraph.getDocument().getPosOfParagraph(paragraph);
        replacePlaceholder(paragraph, "{{insert}}", "");
        XWPFTable table = paragraph.getDocument().insertNewTbl(paragraph.getCTP().newCursor());
        table.setWidth("100%");
        XWPFTableRow headerRow = table.getRow(0);
        headerRow.getCell(0).setText("Наименование");
        headerRow.addNewTableCell().setText("Кол-во");
        headerRow.addNewTableCell().setText("Цена");
        for (DishInfo dishInfo : dishInfoList) {
            XWPFTableRow row = table.createRow(); // Создаем новую строку
            row.getCell(0).setText(dishInfo.getName()); // Наименование
            row.getCell(1).setText(String.valueOf(dishInfo.getQuantity())); // Количество
            row.getCell(2).setText(String.valueOf(dishInfo.getCost())); // Цена
        }
    }

    private void replacePlaceholder(XWPFParagraph paragraph, String placeholder, String value) {
        StringBuilder paragraphText = new StringBuilder();
        for (XWPFRun run : paragraph.getRuns()) {
            String text = run.getText(0);
            if (text != null) {
                paragraphText.append(text);
            }
        }

        // Замена заполнителя в собранном тексте
        String updatedText = paragraphText.toString().replace(placeholder, value);

        // Удаляем все `XWPFRun` в абзаце
        for (int i = paragraph.getRuns().size() - 1; i >= 0; i--) {
            paragraph.removeRun(i);
        }

        // Добавляем обновленный текст обратно
        XWPFRun newRun = paragraph.createRun();
        newRun.setText(updatedText);
    }
}

// Класс для хранения информации о блюде
class DishInfo {
    private String name;
    private int quantity;
    private Double cost;

    public DishInfo(String name, int quantity, Double cost) {
        this.name = name;
        this.quantity = quantity;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getCost() {
        return cost;
    }


}