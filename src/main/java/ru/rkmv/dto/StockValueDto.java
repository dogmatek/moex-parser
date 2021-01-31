package ru.rkmv.dto;

import lombok.Data;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDate;


@Setter
@Data
public class StockValueDto {
    private String  stock;
    private LocalDate date;
    private Float open;
    private Float high;
    private Float low;
    private Float close;
    private Integer vol;
}
