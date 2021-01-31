package ru.rkmv.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.rkmv.entities.StockValueEntity;

import java.time.LocalDate;

public interface StockValueRepository extends CrudRepository<StockValueEntity, Integer> {

    @Query("DELETE from stock_value where stock =:stock and  date =:date")
    void delete(@Param("stock") String stock, @Param("date") String date);
}


