package ru.comfortsoft.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.comfortsoft.task.service.ExcelParserService;
import ru.comfortsoft.task.service.MinFinderService;

import java.io.IOException;
import java.util.List;

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
    @ApiResponse(responseCode = "200", description = "Успешная обработка",
            content = @Content(examples = @ExampleObject("5")))
    @ApiResponse(responseCode = "400", description = "Неверные параметры входного запроса",
            content = @Content(examples = @ExampleObject("N должно быть положительным и меньше или равно числу элементов в столбце")))
    @PostMapping(value = "/min-number", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> findNthMinimum(@RequestParam MultipartFile file, @RequestParam int N) throws IOException {
        List<Integer> values = excelParserService.extractValues(file);
        Integer nthMinimum = minFinderService.findNthMinimum(values, N);
        return new ResponseEntity<>(nthMinimum.toString(), HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
