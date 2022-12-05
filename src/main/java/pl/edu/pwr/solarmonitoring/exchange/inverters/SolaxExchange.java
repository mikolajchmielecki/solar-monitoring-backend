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

package pl.edu.pwr.solarmonitoring.exchange.inverters;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;;
import pl.edu.pwr.solarmonitoring.model.SolaxInverter;
import pl.edu.pwr.solarmonitoring.model.response.InverterParametersResponse;

@Service
@Slf4j
public class SolaxExchange {

    private static final String URL = "https://www.solaxcloud.com:9443/proxy/api/getRealtimeInfo.do?tokenId=%s&sn=%s";

    public static InverterParametersResponse getInverterParameters(SolaxInverter solaxInverter) {
        try {
            JsonObject response = getInverterResponse(solaxInverter);
            Status status = getStatus(response);
            if (status != Status.COMMUNICATION_ERROR) {
                return InverterParametersResponse.builder()
                        .status(status.name())
                        .currentPower(getCurrentPower(response))
                        .todayYield(getTodayYield(response))
                        .totalYield(getTotalYield(response))
                        .build();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return InverterParametersResponse.builder().status(Status.COMMUNICATION_ERROR.name()).build();
    }

    private static Double getTodayYield(JsonObject response) {
        return response.getAsJsonObject("result").get("yieldtoday").getAsDouble();
    }

    private static Double getTotalYield(JsonObject response) {
        return response.getAsJsonObject("result").get("yieldtotal").getAsDouble();
    }

    private static Double getCurrentPower(JsonObject response) {
        return response.getAsJsonObject("result").get("acpower").getAsDouble();
    }

    private static Status getStatus(JsonObject response) {
        try {
            if (response.get("success").getAsBoolean()) {
                switch (response.getAsJsonObject("result").get("inverterStatus").getAsInt()) {
                    case 102:
                        return Status.ACTIVE;
                    case 100:
                    case 101:
                    case 105:
                    case 110:
                        return Status.STANDBY;
                    default:
                        return Status.ALARM;
                }
            } else {
                return Status.COMMUNICATION_ERROR;
            }
        } catch (Exception e ) {
            return Status.COMMUNICATION_ERROR;
        }
    }

    private static JsonObject getInverterResponse(SolaxInverter inverter) {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(String.format(URL, inverter.getTokenId(), inverter.getSerialNumber()), String.class);
        return JsonParser.parseString(response).getAsJsonObject();
    }

}
