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

package pl.edu.pwr.solarmonitoring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.response.StringResponse;
import pl.edu.pwr.solarmonitoring.scheduling.ScheduledTasks;
import pl.edu.pwr.solarmonitoring.service.EnergyService;
import pl.edu.pwr.solarmonitoring.utils.UserUtils;

@RestController
@RequestMapping("api/v1/energy")
@RequiredArgsConstructor
public class EnergyController {

    private final ScheduledTasks scheduledTasks;
    private final EnergyService energyService;

    /**
     * only for testing
     * @return
     */
    @GetMapping("/run")
    public ResponseEntity getEnergyInBeforeMonth() {
        scheduledTasks.saveMonthlyEnergaData();
        scheduledTasks.saveMonthlyInvertersData();
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/get/{year}")
    public ResponseEntity getArchivedEnergy(Authentication authentication, @PathVariable Integer year) {
        User user = UserUtils.fromAuthentication(authentication);
        try {
            return ResponseEntity.ok(energyService.getArchivedEnergy(user, year));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new StringResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
