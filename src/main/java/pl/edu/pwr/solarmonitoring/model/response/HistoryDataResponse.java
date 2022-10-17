package pl.edu.pwr.solarmonitoring.model.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class HistoryDataResponse {

    private LocalDate date;
    private Double value;

}
