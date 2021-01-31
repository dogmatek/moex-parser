package ru.rkmv.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.rkmv.dto.StockValueDto;
import ru.rkmv.services.StockValueService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class StockValueController {


    private final StockValueService stockValueService;

    @GetMapping
    public List<StockValueDto> getAll() {
        return stockValueService.getAll();
    }


//
//    @GetMapping
//    public List<StockValueDto> findAll() {
//        return stockValueService.getAll();
//    }


}
