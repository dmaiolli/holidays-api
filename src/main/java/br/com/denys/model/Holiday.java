package br.com.denys.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Holiday {

    private static final String IS_NOT_HOLIDAY = "Não é um feriado";

    private String id;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;

    private Boolean isHoliday;


    public static Holiday workingDay(LocalDate date) {
        return Holiday.builder()
                .description(IS_NOT_HOLIDAY)
                .date(date)
                .isHoliday(false)
                .build();
    }

}
