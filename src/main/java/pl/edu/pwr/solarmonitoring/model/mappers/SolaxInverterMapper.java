package pl.edu.pwr.solarmonitoring.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.edu.pwr.solarmonitoring.model.SolaxInverter;
import pl.edu.pwr.solarmonitoring.model.request.SolaxRequest;

@Mapper
public interface SolaxInverterMapper {

    SolaxInverterMapper INSTANCE = Mappers.getMapper(SolaxInverterMapper.class);

    SolaxInverter requestToEntity(SolaxRequest request);

}

