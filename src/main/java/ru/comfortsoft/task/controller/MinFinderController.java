package ru.comfortsoft.task.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import ru.comfortsoft.task.service.ExcelParserService;
import ru.comfortsoft.task.service.MinFinderService;

import java.io.IOException;
import java.util.Set;

@Tag(name = "Нахождение N-го минимального числа")
@RestController
public class MinFinderController {

    private final ExcelParserService excelParserService;
    private final MinFinderService minFinderService;

    @Autowired
    public MinFinderController(ExcelParserService excelParserService, MinFinderService minFinderService) {
        this.excelParserService = excelParserService;
        this.minFinderService = minFinderService;
    }

    @Operation(summary = "Загрузите файл .xlsx и получите N-ое минимальное число из первого столбца")
    @PostMapping(value = "/min-number", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Integer> getMinNumber(@RequestParam MultipartFile file, @RequestParam int N) throws IOException {
        Set<Integer> uniqueValues = excelParserService.extractUniqueValues(file);
        Integer nthMinimum = minFinderService.findNthMinimum(uniqueValues, N);
        return new ResponseEntity<>(nthMinimum, HttpStatus.OK);
    }
}
