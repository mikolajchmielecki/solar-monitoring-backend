package pl.edu.pwr.solarmonitoring.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.edu.pwr.solarmonitoring.model.Counter;
import pl.edu.pwr.solarmonitoring.model.SolaxInverter;
import pl.edu.pwr.solarmonitoring.model.request.CounterRequest;
import pl.edu.pwr.solarmonitoring.model.request.SolaxRequest;

@Mapper
public interface CounterMapper {

    CounterMapper INSTANCE = Mappers.getMapper(CounterMapper.class);

    Counter requestToEntity(CounterRequest request);

}

