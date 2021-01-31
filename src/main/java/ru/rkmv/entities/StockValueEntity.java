package ru.rkmv.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity(name = "stock_value")
public class StockValueEntity {

    @Id
    @AttributeOverrides({
            @AttributeOverride(name = "stock",
            column = @Column(name="stock")),
            @AttributeOverride(name = "date",
            column = @Column(name="date"))
            })
    @Column
    private String  stock;

    @Column
    private LocalDate date;

    @Column
    private Float open;

    @Column
    private Float high;

    @Column
    private Float low;

    @Column
    private Float close;

    @Column
    private Integer vol;
}
