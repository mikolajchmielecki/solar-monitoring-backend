package pl.edu.pwr.solarmonitoring.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.edu.pwr.solarmonitoring.model.HistoryData;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class InverterResponse {

    private Long id;
    private String name;
    private Double beforeEnergy;
    private InverterParametersResponse inverterParameters;

}
