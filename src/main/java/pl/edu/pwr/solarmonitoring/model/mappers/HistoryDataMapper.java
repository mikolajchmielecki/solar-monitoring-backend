package pl.edu.pwr.solarmonitoring.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.edu.pwr.solarmonitoring.model.ChargedHistoryData;
import pl.edu.pwr.solarmonitoring.model.ProducedHistoryData;
import pl.edu.pwr.solarmonitoring.model.RemittedHistoryData;
import pl.edu.pwr.solarmonitoring.model.response.HistoryDataResponse;

@Mapper
public interface HistoryDataMapper {

    HistoryDataMapper INSTANCE = Mappers.getMapper(HistoryDataMapper.class);

    HistoryDataResponse fromChargedHistoryData(ChargedHistoryData chargedHistoryData);
    HistoryDataResponse fromRemittedHistoryData(RemittedHistoryData remittedHistoryData);
    HistoryDataResponse fromProducedHistoryData(ProducedHistoryData producedHistoryData);

}

