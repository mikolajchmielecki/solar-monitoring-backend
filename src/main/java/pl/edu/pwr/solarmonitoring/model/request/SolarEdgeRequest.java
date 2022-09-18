package pl.edu.pwr.solarmonitoring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class SolarEdgeRequest {

    private Long id;
    private String name;
    private String apiKey;
    private String siteId;

}
