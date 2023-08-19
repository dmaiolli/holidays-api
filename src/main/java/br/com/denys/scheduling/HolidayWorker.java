package br.com.denys.scheduling;

import br.com.denys.model.Holiday;
import br.com.denys.repository.HolidayRepository;
import br.com.denys.service.HolidayExtractorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor
public class HolidayWorker {

    private final HolidayRepository repository;
    private final HolidayExtractorService holidayExtractorService;

    @Scheduled(fixedDelay = 1000)
    public void start() {
        log.info("Iniciando a sincronização de feriados");
        try {
            List<Holiday> holidays = holidayExtractorService.extract();
            if(shouldUpdate(holidays.size())) update(holidays);
        } catch (IOException e) {
            log.error("Erro ao extrair XLSL de feriados, ", e);
        }
    }

    private void update(final List<Holiday> holidays) {
        if(!holidays.isEmpty()) {
            log.info("Encontrados {} feriados na listas", holidays.size());
            repository.deleteAll();
            repository.saveAll(holidays);
            // TODO CACHE
        }
    }

    private boolean shouldUpdate(final Integer holidays) {
        return repository.count() != holidays.longValue();
    }

}
