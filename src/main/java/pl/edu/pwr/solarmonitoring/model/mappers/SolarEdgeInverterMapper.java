package pl.edu.pwr.solarmonitoring.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.edu.pwr.solarmonitoring.model.SolarEdgeInverter;
import pl.edu.pwr.solarmonitoring.model.SolaxInverter;
import pl.edu.pwr.solarmonitoring.model.request.SolarEdgeRequest;
import pl.edu.pwr.solarmonitoring.model.request.SolaxRequest;

@Mapper
public interface SolarEdgeInverterMapper {

    SolarEdgeInverterMapper INSTANCE = Mappers.getMapper(SolarEdgeInverterMapper.class);

    SolarEdgeInverter requestToEntity(SolarEdgeRequest request);

}

