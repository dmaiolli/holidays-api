package br.com.denys.service;

import br.com.denys.model.Holiday;
import br.com.denys.network.HolidayClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HolidayExtractorService {

    private static final int DATE_CELL = 0;
    private static final int DESCRIPTION_CELL = 2;
    private static final String FIRST_ROW = "Data";
    private static final String LAST_ROW = "Fonte: ANBIMA";
    private static final int FIRST_SHEET = 0;
    private final HolidayClient holidayClient;

    public List<Holiday> extract() throws IOException {
        byte[] source = holidayClient.holidayXlslFile();
        return map(source);
    }

    public static List<Holiday> map(final byte[] sourceFile) throws IOException {
        final List<Holiday> holidays = new ArrayList<>();

        try(HSSFWorkbook workbook = from(sourceFile)) {
            HSSFSheet sheet = workbook.getSheetAt(FIRST_SHEET);
            for(final Row row : sheet) {
                if(last(row)) {
                    break;
                }

                if(!first(row)) {
                    holidays.add(Holiday.builder()
                            .date(getHolidayLocalDate(row))
                            .description(getDescription(row))
                            .isHoliday(true)
                            .build());
                }
            }
        }

        return holidays;
    }

    private static LocalDate getHolidayLocalDate(final Row row) {
        Cell date = row.getCell(DATE_CELL);
        return date.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private static String getDescription(final Row row) {
        Cell description = row.getCell(DESCRIPTION_CELL);
        return description.getStringCellValue();
    }

    private static boolean last(final Row row) {
        Cell firstCell = row.getCell(DATE_CELL);
        return firstCell.getCellTypeEnum() == CellType.STRING && firstCell.getStringCellValue().equals(LAST_ROW);
    }

    private static boolean first(final Row row) {
        Cell firstCell = row.getCell(DATE_CELL);
        return firstCell.getCellTypeEnum() == CellType.STRING && firstCell.getStringCellValue().equals(FIRST_ROW);
    }

    private static HSSFWorkbook from(final byte[] sourceFile) throws IOException {
        try (InputStream inputStream = new ByteArrayInputStream(sourceFile)) {
            return new HSSFWorkbook(inputStream);
        }
    }

}
