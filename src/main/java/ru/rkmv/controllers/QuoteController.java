package ru.rkmv.controllers;

import jdk.nashorn.api.scripting.URLReader;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rkmv.dto.QuoteDto;
import ru.rkmv.entities.QuoteEntity;
import ru.rkmv.repositories.QuoteRepository;
import ru.rkmv.services.QuoteService;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class QuoteController {

    private final QuoteService quoteService;


    @GetMapping
    public List<QuoteDto> getAll() {
        return quoteService.getAll();
    }


    @GetMapping("/get/{stock}/{date}")
    public QuoteDto getfindByStockAndDate(@PathVariable String stock, @PathVariable String date) {
        return quoteService.getfindByStockAndDate(stock,
                LocalDate.parse(date, DateTimeFormatter.ofPattern("u-M-d"))
                );
    }

    // Парсер
    @GetMapping("json")
    public void showJson() throws IOException, ParseException {
        int s =0;
        JSONParser parser = new JSONParser();
        List<QuoteDto> quoteDtos = new ArrayList<>();

        try (Reader reader = new URLReader(new URL("https://iss.moex.com/iss/history/engines/stock/markets/shares/securities.json?date=2021-02-11"))) {

            JSONArray stocks = (JSONArray) ((JSONObject) ((JSONObject) parser.parse(reader)).get("history")).get("data");
            Iterator<JSONArray> iterator = stocks.iterator();
            while (iterator.hasNext()) {
//                if(++s >= 50) break;
                JSONArray stock = iterator.next();
                System.out.println("date = " + stock.get(1));
                System.out.println("stock = " + stock.get(3));
                System.out.println("open = " + stock.get(6));
                System.out.println("high = " + stock.get(8));
                System.out.println("low = " + stock.get(7));
                System.out.println("close = " + stock.get(11));
                System.out.println("vol = " + stock.get(12));

                if (stock.get(6) == null) continue;

                QuoteDto dto = new QuoteDto();
                dto.setStock((String) stock.get(3));
                dto.setDate( LocalDate.parse((String) stock.get(1), DateTimeFormatter.ofPattern("u-M-d")));
                dto.setOpen(Double.valueOf( stock.get(6).toString()));
                dto.setHigh(Double.valueOf( stock.get(8).toString()));
                dto.setLow(Double.valueOf( stock.get(7).toString()));
                dto.setClose(Double.valueOf( stock.get(11).toString()));
                dto.setVol( Integer.valueOf(stock.get(12).toString()));

                quoteDtos.add(dto);

                System.out.println("------------quoteDtos.add(dto)------------");
            }
            System.out.println("------------START quoteService.save(quoteDtos)------------");
            System.out.println(quoteService.save(quoteDtos));
            System.out.println("------------END quoteService.save(quoteDtos)------------");

        } catch (IOException e) {
            System.out.println("--------------------- IOException ------------------");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("--------------------- ParseException ------------------");
            e.printStackTrace();
        }

    }
}
