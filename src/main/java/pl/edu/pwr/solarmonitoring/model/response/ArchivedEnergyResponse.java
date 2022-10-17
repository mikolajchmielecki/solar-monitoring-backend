package pl.edu.pwr.solarmonitoring.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Map;

@Data
@Builder
public class ArchivedEnergyResponse {

    private Integer year;
    private Collection<HistoryDataResponse> remitEnergy;
    private Collection<HistoryDataResponse> chargeEnergy;
    private Collection<HistoryDataResponse> totalProducedEnergy;
    private Map<String, Collection<HistoryDataResponse>> producedEnergy;

}
