package ru.rkmv.repositories;

import java.time.LocalDate;
import java.util.List;

public interface CommonRepository<T> {

    public T save(T quote);
    public boolean save(List<T> quotes);
    public T findByStockAndDate(String stock, LocalDate date);

    public Iterable<T> findAll();
}
