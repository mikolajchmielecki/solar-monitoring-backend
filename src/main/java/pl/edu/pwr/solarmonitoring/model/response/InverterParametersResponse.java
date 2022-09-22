package pl.edu.pwr.solarmonitoring.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InverterParametersResponse {

    private Double todayYield;
    private Double totalYield;
    private Double currentPower;
    private String status;

}
