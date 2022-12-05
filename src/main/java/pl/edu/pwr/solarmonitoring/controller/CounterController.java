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
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.request.CounterRequest;
import pl.edu.pwr.solarmonitoring.model.response.CounterResponse;
import pl.edu.pwr.solarmonitoring.model.response.StringResponse;
import pl.edu.pwr.solarmonitoring.service.CounterService;
import pl.edu.pwr.solarmonitoring.utils.UserUtils;

@RestController
@RequestMapping("/api/v1/counter")
@RequiredArgsConstructor
public class CounterController {

    private final CounterService counterService;

    @PatchMapping("update")
    public ResponseEntity updateCounter(Authentication authentication, @RequestBody CounterRequest counterRequest) {
        User user = UserUtils.fromAuthentication(authentication);
        try {
            counterService.update(user, counterRequest);
            return new ResponseEntity(new StringResponse("Counter " + counterRequest + " was updated."), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new StringResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("get")
    public ResponseEntity getCounter(Authentication authentication) {
        User user = UserUtils.fromAuthentication(authentication);
        try {
            CounterResponse counterResponse = counterService.get(user);
            return new ResponseEntity(counterResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new StringResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
