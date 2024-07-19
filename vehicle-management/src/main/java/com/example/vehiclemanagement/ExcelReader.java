package com.example.vehiclemanagement;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {

    public static List<FieldDefinition> readFieldDefinitions(String excelFilePath) throws Exception {
        List<FieldDefinition> fieldDefinitions = new ArrayList<>();

        FileInputStream file = new FileInputStream(new File(excelFilePath));
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue; // Skip header row
            }

            FieldDefinition fieldDefinition = new FieldDefinition();
            fieldDefinition.setName(row.getCell(0).getStringCellValue());
            fieldDefinition.setStartPos((int) row.getCell(1).getNumericCellValue() - 1); // Adjust for 0-based index
            fieldDefinition.setInputType(row.getCell(2).getStringCellValue());
            fieldDefinition.setLength((int) row.getCell(3).getNumericCellValue());

            fieldDefinitions.add(fieldDefinition);
        }

        workbook.close();
        file.close();

        return fieldDefinitions;
    }
}
