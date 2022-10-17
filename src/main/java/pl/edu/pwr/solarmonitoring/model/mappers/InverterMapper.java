package pl.edu.pwr.solarmonitoring.model.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.edu.pwr.solarmonitoring.model.Inverter;
import pl.edu.pwr.solarmonitoring.model.response.InverterResponse;

@Mapper
public interface InverterMapper {

    InverterMapper INSTANCE = Mappers.getMapper(InverterMapper.class);

    InverterResponse entityToResponse(Inverter entity);

}

