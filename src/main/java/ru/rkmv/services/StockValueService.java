package ru.rkmv.services;


public class StockValueService {


//
//    private final StockValueRepository stockValueRepository;
//
//    // сохранить данные
//    public StockValueDto save(StockValueDto dto) {
//        // Валидация
//        String validation = "";
////        if (dto.getStock().equals("")) validation += "Не указан Stock. \n";
////        if (dto.getDate().getYear() < 1900) validation += "Некорректная Дата. \n";
//
//        if (!validation.equals("")) {
//
//            return new StockValueDto();
//        }
//        StockValueEntity stockValueEntity = mapToEntity(dto);
//        try {
//            return mapToDto(stockValueRepository.save(stockValueEntity));
//        } catch (Exception ex) {
//
//        }
//        return null;
//    }
//
//
//    // Вернуть все записи
//    public List<StockValueDto> getAll() {
//        try {
//            return StreamSupport.stream(stockValueRepository.findAll().spliterator(), false)
//                    .map(this::mapToDto)
//                    .collect(Collectors.toList());
//        } catch (Exception ex) {
//
//        }
//        return null;
//    }
//
//
//    // Удаление
//    public void delete(String stock, LocalDate date) {
//        try {
////            StockValueRepository.delete(stock, date.toString());
//        } catch (Exception ex) {
//
//        }
//    }
//
//
//


}
