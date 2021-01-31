package ru.rkmv.services;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.rkmv.entities.LogEntity;
import ru.rkmv.repositories.LogRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    private final static String KEY_LOGGEDSERVICE = "moex-parser";


    public void add(String log) {

        // Сохранение в базу данных
        // Возникла проблема единовременного занесения новой записи в базу данных,
        // так как создается один и тот же ID при работе серверной частьи и iot
        LogEntity logEntity = new LogEntity();
        logEntity.setKey(KEY_LOGGEDSERVICE);
        logEntity.setDescription(log);
        logEntity.setDt(LocalDateTime.now());

        logRepository.save(logEntity);
    }

    //Добавить лог в кафку/базу данных и вызвать исключение
    @ExceptionHandler()
    public void addAndExeption(String message) {
        add(message);
//        Throw new Exception(message);
    }





}
