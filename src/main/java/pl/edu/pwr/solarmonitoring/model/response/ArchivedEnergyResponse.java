package pl.edu.pwr.solarmonitoring.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class ArchivedEnergyResponse {

    private String name;
    private List<Double> data;

}
