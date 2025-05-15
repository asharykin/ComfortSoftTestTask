package ru.comfortsoft.task.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class ExcelParserService {

    public Set<Integer> extractUniqueValues(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        Set<Integer> uniqueValues = new HashSet<>();
        for (Row row : sheet) {
            Cell cell = row.getCell(0);
            if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                uniqueValues.add((int) cell.getNumericCellValue());
            }
        }

        workbook.close();
        return uniqueValues;
}
}
