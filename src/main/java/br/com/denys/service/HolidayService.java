package br.com.denys.service;

import br.com.denys.model.Holiday;
import br.com.denys.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayService {

    private final HolidayRepository repository;

    private static final String DATE_FORMAT = "dd-MM-yyyy";

    public List<Holiday> ofYear(int year) {
        LocalDate firstDay = LocalDate.of(year, 1, 1);
        LocalDate lastDay = firstDay.with(TemporalAdjusters.lastDayOfYear());
        return repository.findByDateBetween(firstDay, lastDay);
    }

    public Holiday ofDate(String date) {
        LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
        return ofDay(parsedDate);
    }

    public Holiday ofDay(LocalDate date) {
        return repository.findFirstByDate(date).orElseGet(() -> Holiday.workingDay(date));
    }

}
