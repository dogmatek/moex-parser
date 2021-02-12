package ru.rkmv.entities;

import lombok.Data;

import java.time.LocalDate;

@Data
public class QuoteEntity {
    private String stock;
    private LocalDate date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Integer vol;

//    public QuoteEntity(){
//        this.date = LocalDate.now();
//        this.stock = "ff";
//        this.open = 12.55;
//    }

}
