package pl.edu.pwr.solarmonitoring.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class InverterResponse {

    private Long id;
    private String name;
    private Double beforeEnergy;
    private InverterParametersResponse inverterParameters;
    private Map<String, String> credentials;
    private String type;

}
