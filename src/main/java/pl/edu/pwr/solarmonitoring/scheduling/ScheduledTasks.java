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

package pl.edu.pwr.solarmonitoring.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.edu.pwr.solarmonitoring.exchange.energa.EnergaData;
import pl.edu.pwr.solarmonitoring.exchange.energa.EnergaExchange;
import pl.edu.pwr.solarmonitoring.model.ChargedHistoryData;
import pl.edu.pwr.solarmonitoring.model.Inverter;
import pl.edu.pwr.solarmonitoring.model.ProducedHistoryData;
import pl.edu.pwr.solarmonitoring.model.RemittedHistoryData;
import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.repository.UserRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {

    private static final int REPEATS = 5;

    private final UserRepository userRepository;
    private final EnergaExchange energaExchange;

    /**
     * Run every 5'th day of month to save energa data for each user
     */
    @Scheduled(cron = "0 0 0 5 * *")
    public void saveMonthlyEnergaData() {
        log.info("Started saveMonthlyEnergaData procedure");
        Collection<User> users = userRepository.findAll();
        for (User user : users) {
            saveMonthlyEnergaDataForUser(user);
        }
        userRepository.saveAll(users);
    }

    /**
     * Run every first day of month to save produced energy data by inverters data for each user
     */
    @Scheduled(cron = "0 0 1 1 * *")
    public void saveMonthlyInvertersData() {
        log.info("Started saveMonthlyInvertersData procedure");
        Collection<User> users = userRepository.findAll();
        for (User user : users) {
            saveMonthlyInvertersDataForUser(user);
        }
        userRepository.saveAll(users);
    }

    private void saveMonthlyEnergaDataForUser(User user) {
        for (int i = 0; i < REPEATS; i++) {
            try {
                EnergaData energaData = energaExchange.getEnergaDataForBeforeMonth(user);

                // remove duplicates
                user.getCounter().getChargeEnergy().removeAll(user.getCounter().getChargeEnergy().stream()
                        .filter(d -> d.getDate().equals(getFirstDayOfBeforeMonth()))
                        .collect(Collectors.toList()));
                user.getCounter().getRemitEnergy().removeAll(user.getCounter().getRemitEnergy().stream()
                        .filter(d -> d.getDate().equals(getFirstDayOfBeforeMonth()))
                        .collect(Collectors.toList()));

                // add values
                user.getCounter().getChargeEnergy().add(ChargedHistoryData.builder()
                        .value(energaData.getChargeEnergy())
                        .date(getFirstDayOfBeforeMonth())
                        .counter(user.getCounter())
                        .build());
                user.getCounter().getRemitEnergy().add(RemittedHistoryData.builder()
                        .value(energaData.getRemitEnergy())
                        .date(getFirstDayOfBeforeMonth())
                        .counter(user.getCounter())
                        .build());
                userRepository.save(user);
                return;
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    private void saveMonthlyInvertersDataForUser(User user) {
        for (Inverter inverter : user.getInverters()) {
            saveMonthlyDataForInverter(inverter);
        }
    }

    private void saveMonthlyDataForInverter(Inverter inverter) {
        for (int i = 0; i < REPEATS; i++) {
            try {
                Double totalYield = inverter.getInverterParameters().getTotalYield();
                if (inverter.getBeforeEnergy() == null) {
                    inverter.setBeforeEnergy(totalYield);
                    return;
                }

                // remove duplicates
                inverter.getProducedEnergy().removeAll(inverter.getProducedEnergy().stream()
                        .filter(d -> d.getDate().equals(getFirstDayOfBeforeMonth())).collect(Collectors.toList()));


                // add
                inverter.getProducedEnergy().add(ProducedHistoryData.builder()
                                .date(getFirstDayOfBeforeMonth())
                                .value(totalYield - inverter.getBeforeEnergy())
                                .inverter(inverter)
                                .build());
                return;
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    private static LocalDate getFirstDayOfBeforeMonth() {
        return LocalDate.now().minusMonths(1).withDayOfMonth(1);
    }
}
