package ru.rkmv.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rkmv.dto.StockValueDto;
import ru.rkmv.entities.StockValueEntity;
import ru.rkmv.repositories.StockValueRepository;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class StockValueService {


    private final LogService logService;
    private final StockValueRepository stockValueRepository;

    // сохранить данные
    public StockValueDto save(StockValueDto dto) {
        // Валидация
        String validation = "";
        if (dto.getStock().equals("")) validation += "Не указан Stock. \n";
        if (dto.getDate().getYear() < 1900) validation += "Некорректная Дата. \n";

        if (!validation.equals("")) {
            logService.addAndExeption("Ошибка валидации StockValueService.save: " + validation);
            return new StockValueDto();
        }
        StockValueEntity stockValueEntity = mapToEntity(dto);
        try {
            return mapToDto(stockValueRepository.save(stockValueEntity));
        } catch (Exception ex) {
            logService.addAndExeption("Ошибка запроса StockValueService.save: " + ex.getMessage());
        }
        return null;
    }


    // Вернуть все записи
    public List<StockValueDto> getAll() {
        try {
            return StreamSupport.stream(stockValueRepository.findAll().spliterator(), false)
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            logService.addAndExeption("Ошибка запроса ServoService.getAll: " + ex.getMessage());
        }
        return null;
    }


    // Удаление
    public void delete(String stock, LocalDate date) {
        try {
//            StockValueRepository.delete(stock, date.toString());
        } catch (Exception ex) {
            logService.addAndExeption("Ошибка запроса ServoService.delete: " + ex.getMessage());
        }
    }


    private StockValueEntity mapToEntity(StockValueDto dto) {
        StockValueEntity entity = new StockValueEntity();
        entity.setStock(dto.getStock());
        entity.setDate(dto.getDate());
        entity.setOpen(dto.getOpen());
        entity.setHigh(dto.getHigh());
        entity.setLow(dto.getLow());
        entity.setClose(dto.getClose());
        entity.setVol(dto.getVol());
        return entity;
    }


    private StockValueDto mapToDto(StockValueEntity entity) {
        StockValueDto dto = new StockValueDto();
        dto.setStock(entity.getStock());
        dto.setDate(entity.getDate());
        dto.setOpen(entity.getOpen());
        dto.setHigh(entity.getHigh());
        dto.setLow(entity.getLow());
        dto.setClose(entity.getClose());
        dto.setVol(entity.getVol());
        return dto;
    }


}
