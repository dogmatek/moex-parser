package ru.rkmv.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.rkmv.entities.QuoteEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class QuoteRepository implements CommonRepository<QuoteEntity> {
    private static final String SQL_INSERT = "insert into stock_value (stock, date, open, high, low, close, vol) " +
            "values ";
    //            "values (:stock,:date,:open,:high,:low, :close, :vol)";
//            "values (?,?,?,:?,?, ?,?)";
    private static final String SQL_QUERY_FIND_ALL = "select stock, date, open, high, low, close, vol from stock_value";
    //    private static final String SQL_QUERY_FIND_BY_STOCK_AND_DATE = SQL_QUERY_FIND_ALL + " where stock = :stock AND date = :date";
//    private static final String SQL_QUERY_FIND_BY_STOCK_AND_DATE = SQL_QUERY_FIND_ALL + " where stock = :stock AND date = :date";
    private static final String SQL_QUERY_FIND_BY_STOCK_AND_DATE = SQL_QUERY_FIND_ALL + " where stock = :stock and vol= :vol";
    private static final String SQL_UPDATE = "update stock_value set open = :open, high = :high, low = :low, close = :close, vol = :vol " +
            " where stock = :stock AND date = :date";
    private static final String SQL_DELETE = "delete from stock_value where stock = :stock AND date = :date";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public QuoteRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//    @Autowired
//    private final NamedParameterJdbcTemplate jdbcTemplate;

    private RowMapper<QuoteEntity> quoteRowMapper = (ResultSet rs, int rowNum) -> {
        QuoteEntity entity = new QuoteEntity();
        entity.setStock(rs.getString("stock"));
        entity.setDate(
                LocalDate.parse((String) rs.getString("date"), DateTimeFormatter.ofPattern("u-M-d"))
        );
        entity.setOpen(rs.getDouble("open"));
        entity.setHigh(rs.getDouble("high"));
        entity.setLow(rs.getDouble("low"));
        entity.setClose(rs.getDouble("close"));
        entity.setVol(rs.getInt("vol"));
        return entity;
    };


    @Override
    public QuoteEntity save(QuoteEntity quote) {
        System.out.println("QuoteRepository save(QuoteEntity quote)");
        QuoteEntity result = findByStockAndDate(quote.getStock(), quote.getDate());
        System.out.println("HELLO");
        System.out.println("result=" + result);
        if (result != null) {
            result.setStock(quote.getStock());
            result.setDate(quote.getDate());
            result.setOpen(quote.getOpen());
            result.setHigh(quote.getHigh());
            result.setLow(quote.getLow());
            result.setClose(quote.getClose());
            result.setVol(quote.getVol());
            System.out.println("SQL_UPDATE = " + SQL_UPDATE);
            return upsert(result, SQL_UPDATE);
        }
        System.out.println("SQL_INSERT = " + SQL_INSERT);
        return upsert(quote, SQL_INSERT);
    }

    /*
        Сохранение списка котировок
     */
    @Override
    public boolean save(List<QuoteEntity> quotes) {
        System.out.println("QuoteRepository save(List<QuoteEntity> quotes)");

        StringBuffer query = new StringBuffer(SQL_INSERT);
        boolean flag = false;
        for (QuoteEntity quote : quotes) {
            query.append(String.format("%s('%s','%s','%s','%s','%s','%s','%d')",
                    ((flag) ? "," : ""),
                    quote.getStock(),
                    quote.getDate(),
                    quote.getOpen(),
                    quote.getHigh(),
                    quote.getLow(),
                    quote.getClose(),
                    quote.getVol()
            ));
            flag = true;
        }
        query.append(" ON CONFLICT (stock, date) DO NOTHING");
        System.out.println(query);
        jdbcTemplate.getJdbcTemplate().execute(query.toString());
        System.out.println("------ GOOD ---------");
        return true;
    }


    private QuoteEntity upsert(final QuoteEntity quote, final String sql) {
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("stock", quote.getStock());
        namedParameters.put("date", quote.getDate());
        namedParameters.put("open", quote.getOpen());
        namedParameters.put("high", quote.getClose());
        namedParameters.put("low", quote.getLow());
        namedParameters.put("close", quote.getClose());
        namedParameters.put("vol", quote.getVol());

        System.out.println(namedParameters);
        this.jdbcTemplate.update(sql, namedParameters);

        return findByStockAndDate(quote.getStock(), quote.getDate());
    }


    @Override
    public QuoteEntity findByStockAndDate(String stock, LocalDate date) {
        System.out.println("QuoteRepository indById(String stock, LocalDate date)");
        try {
            Map<String, String> namedParameters = new HashMap<>();
//            namedParameters.put("stock", stock);
//            namedParameters.put("vol", "67903700" );

//            System.out.println(" дальше парсер даты." + date.toString());
//            namedParameters.put("date", "28-01-2021");

            QuoteEntity entity = this.jdbcTemplate.queryForObject(SQL_QUERY_FIND_ALL
                            + " where stock = '" + stock + "' AND date = '" + date.toString() + "'"
                    , namedParameters, quoteRowMapper);

            //   queryForObject(SQL_QUERY_FIND_BY_STOCK_AND_DATE, namedParameters, quoteRowMapper);

//            System.out.println("entity = " + entity);

            return entity;
        } catch (EmptyResultDataAccessException ex) {
            System.out.println("QuoteRepository findById(String stock, LocalDate date) EmptyResultDataAccessException");
            return null;
        }
    }

    @Override
    public Iterable<QuoteEntity> findAll() {
        return this.jdbcTemplate.query(SQL_QUERY_FIND_ALL, quoteRowMapper);
    }

}
