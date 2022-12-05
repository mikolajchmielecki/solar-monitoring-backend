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

package pl.edu.pwr.solarmonitoring.service;

import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.request.SolarEdgeRequest;
import pl.edu.pwr.solarmonitoring.model.request.SolaxRequest;
import pl.edu.pwr.solarmonitoring.model.response.InverterParametersResponse;
import pl.edu.pwr.solarmonitoring.model.response.InverterResponse;

import java.util.Set;

public interface InverterService {

    void add(User user, SolaxRequest request);
    void add(User user, SolarEdgeRequest request);
    void update(User user, SolaxRequest request);
    void update(User user, SolarEdgeRequest request);
    void delete(User user, Long id);
    InverterParametersResponse getParameters(User user, Long id);
    Set<InverterResponse> findAllInverters(User user);
    InverterResponse getInverter(User user, Long id);

}
