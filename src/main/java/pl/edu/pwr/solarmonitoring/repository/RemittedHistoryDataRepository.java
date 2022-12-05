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

package pl.edu.pwr.solarmonitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.solarmonitoring.model.Counter;
import pl.edu.pwr.solarmonitoring.model.RemittedHistoryData;

import java.time.LocalDate;
import java.util.List;

public interface RemittedHistoryDataRepository extends JpaRepository<RemittedHistoryData, Long> {

    List<RemittedHistoryData> findRemittedHistoryDataByCounterAndDateBetweenOrderByDate(Counter counter, LocalDate start, LocalDate end);

}
