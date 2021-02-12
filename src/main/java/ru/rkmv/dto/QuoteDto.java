package ru.rkmv.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class QuoteDto {
    private String stock;
    private LocalDate date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Integer vol;
}
