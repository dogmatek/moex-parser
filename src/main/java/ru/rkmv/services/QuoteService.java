package ru.rkmv.services;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rkmv.dto.QuoteDto;
import ru.rkmv.entities.QuoteEntity;
import ru.rkmv.repositories.QuoteRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class QuoteService {


    private final QuoteRepository quoteRepository;



    // сохранить данные 1 шт.
    public QuoteDto save(QuoteDto dto) {

        System.out.println("QuoteService save(QuoteDto dto) " );
        // Валидация
        String validation = "";
//        if (dto.getStock().equals("")) validation += "Не указан Stock. \n";
//        if (dto.getDate().getYear() < 1900) validation += "Некорректная Дата. \n";

        if (!validation.equals("")) {
            return new QuoteDto();
        }


        QuoteEntity quoteEntity = mapToEntity(dto);
        try {
            return mapToDto(quoteRepository.save(quoteEntity));
        } catch (Exception ex) {

        }
        return null;
    }

    // сохранить данные 1 шт.
    public boolean save(List<QuoteDto> dtos) {

        System.out.println("----------------QuoteService save(List<QuoteDto> dtos) --------------" );
        // Валидация
        String validation = "";
//        if (dto.getStock().equals("")) validation += "Не указан Stock. \n";
//        if (dto.getDate().getYear() < 1900) validation += "Некорректная Дата. \n";

//        if (!validation.equals("")) {
//            return false;
//        }
        List<QuoteEntity> quoteEntities = StreamSupport.stream(dtos.spliterator(), false)
                .map(this::mapToEntity)
                .collect(Collectors.toList());
//        System.out.println(quoteEntities);

        try {
            return  quoteRepository.save(quoteEntities);
//            return true;

        } catch (Exception ex) {

        }
        return false;
    }



    // Вернуть
    public QuoteDto getfindByStockAndDate(String stock, LocalDate date) {
        try {
            return mapToDto(quoteRepository.findByStockAndDate(stock, date));
        } catch (Exception ex) {

        }
        return null;
    }




    // Вернуть все записи
    public List<QuoteDto> getAll() {
        try {
            return StreamSupport.stream(quoteRepository.findAll().spliterator(), false)
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {

        }
        return null;
    }



    private QuoteEntity mapToEntity(QuoteDto dto) {
        QuoteEntity entity = new QuoteEntity();
        entity.setStock(dto.getStock());
        entity.setDate(dto.getDate());
        entity.setOpen(dto.getOpen());
        entity.setHigh(dto.getHigh());
        entity.setLow(dto.getLow());
        entity.setClose(dto.getClose());
        entity.setVol(dto.getVol());
        return entity;
    }


    private QuoteDto mapToDto(QuoteEntity entity) {
        QuoteDto dto = new QuoteDto();
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
