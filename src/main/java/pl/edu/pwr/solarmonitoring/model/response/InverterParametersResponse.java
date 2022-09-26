package pl.edu.pwr.solarmonitoring.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InverterParametersResponse {

    // kWH
    private Double todayYield;

    // kWH
    private Double totalYield;

    // kW
    private Double currentPower;

    private String status;

}
