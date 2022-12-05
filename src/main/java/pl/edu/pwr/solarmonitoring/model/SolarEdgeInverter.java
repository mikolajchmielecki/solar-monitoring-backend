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

package pl.edu.pwr.solarmonitoring.model;

import lombok.*;
import pl.edu.pwr.solarmonitoring.exchange.inverters.SolarEdgeExchange;
import pl.edu.pwr.solarmonitoring.exchange.inverters.SolaxExchange;
import pl.edu.pwr.solarmonitoring.exchange.inverters.Status;
import pl.edu.pwr.solarmonitoring.model.response.InverterParametersResponse;
import pl.edu.pwr.solarmonitoring.utils.EncryptionUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "solar_edge_inverters")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@EqualsAndHashCode(callSuper = true)
public class SolarEdgeInverter extends Inverter {

    @Column(nullable = false)
    private String apiKey;

    @Column(nullable = false)
    private String siteId;

    public String getApiKey() {
        return EncryptionUtils.decrypt(apiKey);
    }

    public void setApiKey(String apiKey) {
        this.apiKey = EncryptionUtils.encrypt(apiKey);
    }

    public String getPlainApiKey() {
        return apiKey;
    }
    @Override
    public InverterParametersResponse getInverterParameters() {
        return SolarEdgeExchange.getInverterParameters(this);
    }

    @Override
    public String getType() {
        return "solar-edge";
    }

    @Override
    public Map<String, String> getCredentials() {
        Map<String, String> result = new HashMap<>();
        result.put("apiKey", getApiKey());
        result.put("siteId", getSiteId());
        return result;
    }
}
