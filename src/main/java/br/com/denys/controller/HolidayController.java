package br.com.denys.controller;

import br.com.denys.model.Holiday;
import br.com.denys.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("holidays")
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayService service;

    public ResponseEntity<List<Holiday>> forYear(@PathVariable int year) {
        return ResponseEntity.ok(service.ofYear(year));
    }

}
