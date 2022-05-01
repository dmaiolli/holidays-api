package br.com.denys.network;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class HolidayClient {

    private final RestTemplate restTemplate;
    private final String endpoint;
    private static final byte[] EMPTY_ARRAY = {};

    public HolidayClient(final RestTemplate restTemplate, @Value("${holidays.location}") String endpoint) {
        this.restTemplate = restTemplate;
        this.endpoint = endpoint;
    }

    public byte[] holidayXlslFile() {
        try {
            log.info("Download da planilha de feriados");
            HttpEntity<String> entity = new HttpEntity<>(new HttpHeaders());
            ResponseEntity<byte[]> response = restTemplate.exchange(endpoint, HttpMethod.GET, entity, byte[].class);
            return response.getBody();
        } catch (RestClientException e) {
            log.error("Erro ao efetuar download da planilha de feriados, ", e);
            return EMPTY_ARRAY.clone();
        }
    }
}
