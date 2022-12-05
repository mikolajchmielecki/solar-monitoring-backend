/*
 * SolarMonitoring
 * Copyright (C) 2022 Mikołaj Chmielecki
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * LICENSE file in root directory contains a copy of the GNU General Public License.
 */

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

