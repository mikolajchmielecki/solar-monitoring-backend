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
import pl.edu.pwr.solarmonitoring.exchange.inverters.SolaxExchange;
import pl.edu.pwr.solarmonitoring.model.response.InverterParametersResponse;
import pl.edu.pwr.solarmonitoring.utils.EncryptionUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "solax_inverters")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@EqualsAndHashCode(callSuper = true)
public class SolaxInverter extends Inverter {

    @Column
    private String serialNumber;

    @Column
    private String tokenId;

    public String getTokenId() {
        return EncryptionUtils.decrypt(tokenId);
    }

    public String getPlainTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = EncryptionUtils.encrypt(tokenId);
    }

    @Override
    public InverterParametersResponse getInverterParameters() {
        return SolaxExchange.getInverterParameters(this);
    }

    @Override
    public String getType() {
        return "solax";
    }

    @Override
    public Map<String, String> getCredentials() {
        Map<String, String> result = new HashMap<>();
        result.put("tokenId", getTokenId());
        result.put("serialNumber", getSerialNumber());
        return result;
    }
}
