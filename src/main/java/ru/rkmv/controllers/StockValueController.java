package ru.rkmv.controllers;

import jdk.nashorn.api.scripting.URLReader;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;
import ru.rkmv.dto.StockValueDto;
import ru.rkmv.services.StockValueService;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class StockValueController {


    private final StockValueService stockValueService;

    @GetMapping
    public List<StockValueDto> getAll() {
        return stockValueService.getAll();
    }

    // Парсер
    @GetMapping("json")
    public void showJson() throws IOException, ParseException {
        int s =0;
        JSONParser parser = new JSONParser();

        try (Reader reader = new URLReader(new URL("https://iss.moex.com/iss/history/engines/stock/markets/shares/securities.json?date=2020-12-22"))) {

            JSONArray stocks = (JSONArray) ((JSONObject) ((JSONObject) parser.parse(reader)).get("history")).get("data");
            Iterator<JSONArray> iterator = stocks.iterator();
            while (iterator.hasNext()) {
                if(++s >= 5) return;
                JSONArray stock = iterator.next();
                System.out.println("date = " + stock.get(1));
                System.out.println("stock = " + stock.get(3));
                System.out.println("open = " + stock.get(6));
                System.out.println("high = " + stock.get(8));
                System.out.println("low = " + stock.get(7));
                System.out.println("close = " + stock.get(11));
                System.out.println("vol = " + stock.get(12));


                StockValueDto dto = new StockValueDto();
                dto.setStock((String) stock.get(3));
                dto.setDate( LocalDate.parse((String) stock.get(1), DateTimeFormatter.ofPattern("u-M-d")));
                dto.setOpen(Float.valueOf( stock.get(6).toString()));
                dto.setHigh(Float.valueOf( stock.get(8).toString()));
                dto.setLow(Float.valueOf( stock.get(7).toString()));
                dto.setClose(Float.valueOf( stock.get(11).toString()));
                dto.setVol( Integer.valueOf(stock.get(12).toString()));


                stockValueService.save(dto);
                System.out.println("------------------------");

            }



        } catch (IOException e) {
            System.out.println("--------------------- IOException ------------------");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("--------------------- ParseException ------------------");
            e.printStackTrace();
        }

    }
}