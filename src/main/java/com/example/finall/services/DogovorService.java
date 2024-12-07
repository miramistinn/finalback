package com.example.finall.services;

import com.example.finall.entity.*;
import lombok.AllArgsConstructor;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DogovorService {
    private final MainInformationService mainInformationService;
    private final MenuService menuService;
    private final AdditionalService additionalService;
    private final CalculationService calculationService;

    public void create(Long id) {
        MainInformation mainInformation = mainInformationService.readById(id);
        String templatePath = "F:\\5 сем\\курсач\\договоры\\Dogovor.docx";
        String outputPath = "F:\\5 сем\\курсач\\договоры\\" + mainInformation.getId() + ".docx"; // Путь к выходному файлу

        String name = mainInformation.getName() + " " + mainInformation.getSurname() + " " + mainInformation.getPatronymic();
        List<BMenu> menuList = menuService.readAllByMainId(id);
        List<DishInfo> bar = new ArrayList<>();
        List<DishInfo> hot = new ArrayList<>();
        List<DishInfo> cold = new ArrayList<>();
        List<DishInfo> salat = new ArrayList<>();
        List<DishInfo> deserts = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        List<Additional> dop = additionalService.findByMenuId(id);
        for ( Additional add :dop){
            System.out.println(add.getCost());
        }
        for (BMenu bMenu : menuList) {
            Dish dish = bMenu.getDish();
            if (dish != null) {
                DishInfo dishInfo = new DishInfo(dish.getName(), bMenu.getQuantity(), dish.getCost()* bMenu.getQuantity());
                switch (dish.getCategory().toLowerCase()) {
                    case "бар":
                        bar.add(dishInfo);
                        break;
                    case "горячие закуски":
                        hot.add(dishInfo);
                        break;
                    case "холодные закуски":
                        cold.add(dishInfo);
                        break;
                    case "салаты":
                        salat.add(dishInfo);
                        break;
                    case "десерты":
                        deserts.add(dishInfo);
                        break;
                    default:
                        System.out.println("Неизвестная категория: " + dish.getCategory());
                        break;
                }
            }
        }

        try (FileInputStream fis = new FileInputStream(templatePath);
             XWPFDocument document = new XWPFDocument(fis)) {
            System.out.println("Заполнение заполнителей");

            List<XWPFParagraph> paragraphsToReplace = new ArrayList<>(document.getParagraphs());

            for (XWPFParagraph paragraph : paragraphsToReplace) {
                replacePlaceholder(paragraph, "{{name}}", name);
                replacePlaceholder(paragraph, "{{room}}", String.valueOf(mainInformation.getRoom()));
                replacePlaceholder(paragraph, "{{quantity}}", String.valueOf(mainInformation.getQuantity()));
                replacePlaceholder(paragraph, "{{day}}", String.valueOf(mainInformation.getDateOfCreated().getDayOfMonth()));
                replacePlaceholder(paragraph, "{{month}}", String.valueOf(mainInformation.getDateOfCreated().getMonth()));
                replacePlaceholder(paragraph, "{{year}}", String.valueOf(mainInformation.getDateOfCreated().getYear()));
                replacePlaceholder(paragraph, "{{hour}}", String.valueOf(mainInformation.getDateOfCreated().getHour()));
                replacePlaceholder(paragraph, "{{min}}", String.valueOf(mainInformation.getDateOfCreated().getMinute()));
                replacePlaceholder(paragraph, "{{nday}}", String.valueOf(now.getDayOfMonth()));
                replacePlaceholder(paragraph, "{{nmonth}}", String.valueOf(now.getMonth()));
                replacePlaceholder(paragraph, "{{nyear}}", String.valueOf(now.getYear()));
                replacePlaceholder(paragraph, "{{total}}", String.valueOf(calculationService.calcuate(id)));
                replacePlaceholder(paragraph, "{{totalmenu}}", String.valueOf(calculationService.calcuateMenu(id)));
                replacePlaceholder(paragraph, "{{totaldop}}", String.valueOf(calculationService.calcuateAditional(id)));


                if (paragraph.getText().contains("{{cold}}") && !cold.isEmpty()) {
                    insertTableAfterParagraph(paragraph, cold);
                    replacePlaceholder(paragraph, "{{cold}}", "");
                }
                if (paragraph.getText().contains("{{hot}}") && !hot.isEmpty()) {
                    insertTableAfterParagraph(paragraph, hot);
                    replacePlaceholder(paragraph, "{{hot}}", "");
                }
                if (paragraph.getText().contains("{{salat}}") && !salat.isEmpty()) {
                    insertTableAfterParagraph(paragraph, salat);
                    replacePlaceholder(paragraph, "{{salat}}", "");
                }
                if (paragraph.getText().contains("{{desert}}") && !deserts.isEmpty()) {
                    insertTableAfterParagraph(paragraph, deserts);
                    replacePlaceholder(paragraph, "{{desert}}", "");
                }
                if (paragraph.getText().contains("{{bar}}") && !bar.isEmpty()) {
                    insertTableAfterParagraph(paragraph, bar);
                    replacePlaceholder(paragraph, "{{bar}}", "");
                }
                if (paragraph.getText().contains("{{dop}}") && !dop.isEmpty()) {
                    insertTableDop(paragraph, dop);
                    replacePlaceholder(paragraph, "{{dop}}", "");
                }
                replacePlaceholder(paragraph, "{{salat}}", "");
                replacePlaceholder(paragraph, "{{bar}}", "");
                replacePlaceholder(paragraph, "{{dop}}", "");
                replacePlaceholder(paragraph, "{{desert}}", "");
                replacePlaceholder(paragraph, "{{hot}}", "");
                replacePlaceholder(paragraph, "{{cold}}", "");

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
        System.out.println(dishInfoList.size());
        XWPFDocument doc = paragraph.getDocument();

        // Вставка нового параграфа после текущего абзаца
        int pos = doc.getPosOfParagraph(paragraph);
        XWPFParagraph newParagraph = doc.insertNewParagraph(paragraph.getCTP().newCursor());

        // Создание новой таблицы после нового параграфа
        XWPFTable table = doc.insertNewTbl(newParagraph.getCTP().newCursor());
        table.setWidth("100%");

        // Заполнение заголовков таблицы
        XWPFTableRow headerRow = table.getRow(0);
        setCellText(headerRow.getCell(0), "Наименование");
        setCellText(headerRow.addNewTableCell(), "Кол-во");
        setCellText(headerRow.addNewTableCell(), "Цена, р");

        // Заполнение таблицы данными из dishInfoList
        for (DishInfo dishInfo : dishInfoList) {
            XWPFTableRow row = table.createRow();
            setCellText(row.getCell(0), dishInfo.getName());
            setCellText(row.getCell(1), String.valueOf(dishInfo.getQuantity()));
            setCellText(row.getCell(2), String.valueOf(dishInfo.getCost()));
        }
    }

    private void insertTableDop(XWPFParagraph paragraph, List<Additional> additionals) {
        System.out.println("Inserting additional services table");
        XWPFDocument doc = paragraph.getDocument();

        // Вставка нового параграфа после текущего абзаца
        int pos = doc.getPosOfParagraph(paragraph);
        XWPFParagraph newParagraph = doc.insertNewParagraph(paragraph.getCTP().newCursor());

        // Создание новой таблицы после нового параграфа
        XWPFTable table = doc.insertNewTbl(newParagraph.getCTP().newCursor());
        table.setWidth("100%");

        // Заполнение заголовков таблицы
        XWPFTableRow headerRow = table.getRow(0);
        setCellText(headerRow.getCell(0), "Наименование");
        setCellText(headerRow.addNewTableCell(), "Цена, р");

        // Заполнение таблицы данными из additionals
        for (Additional add : additionals) {
            XWPFTableRow row = table.createRow();
            setCellText(row.getCell(0), add.getAdditionalService().getName());
            setCellText(row.getCell(1), String.valueOf(add.getCost()));
        }
    }

    private void setCellText(XWPFTableCell cell, String text) {
        cell.setText(""); // Очищаем содержимое ячейки
        XWPFRun run = cell.addParagraph().createRun(); // Создаем новый параграф и добавляем текст
        run.setText(text);
        run.setFontFamily("Times New Roman"); // Установка шрифта
        run.setFontSize(12); // Установка размера шрифта
    }

    private void replacePlaceholder(XWPFParagraph paragraph, String placeholder, String value) {
        StringBuilder paragraphText = new StringBuilder();
        for (XWPFRun run : paragraph.getRuns()) {
            String text = run.getText(0);
            if (text != null) {
                paragraphText.append(text);
            }
        }

        String updatedText = paragraphText.toString().replace(placeholder, value);

        for (int i = paragraph.getRuns().size() - 1; i >= 0; i--) {
            paragraph.removeRun(i);
        }

        XWPFRun newRun = paragraph.createRun();
        newRun.setText(updatedText);
    }
}

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