package br.com.denys.scheduling;

import br.com.denys.model.Holiday;
import br.com.denys.service.HolidayExtractorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class HolidayWorker {

    private final RestTemplate restTemplate;
    private final HolidayExtractorService holidayExtractorService;

    @Scheduled(fixedDelay = 1000)
    public void start() {
        log.info("Iniciando a sincronização de feriados");
        try {
            Optional<List<Holiday>> holidays = Optional.ofNullable(holidayExtractorService.extract());
            holidays.ifPresent(this::update);
        } catch (IOException e) {
            log.error("Erro ao extrair XLSL de feriados, ", e);
        }
    }

    public void update(final List<Holiday> holidays) {
        if(!CollectionUtils.isEmpty(holidays)) {
            log.info("Encontrados {} feriados na listas", holidays.size());
            // Deletar todos feriados do banco

            // Salvar todos feriados no banco

        }
    }

}
