package pl.edu.pwr.solarmonitoring.exchange.energa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
public class EnergaData {

    private Double chargeEnergy;
    private Double remitEnergy;

}
