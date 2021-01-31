package ru.rkmv.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.rkmv.entities.LogEntity;


public interface LogRepository extends CrudRepository<LogEntity, Integer> {


}