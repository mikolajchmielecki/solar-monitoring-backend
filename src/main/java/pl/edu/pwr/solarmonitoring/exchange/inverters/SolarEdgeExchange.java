package pl.edu.pwr.solarmonitoring.exchange.inverters;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.edu.pwr.solarmonitoring.model.SolarEdgeInverter;
import pl.edu.pwr.solarmonitoring.model.response.InverterParametersResponse;

@Service
@Slf4j
public class SolarEdgeExchange {

    private static final String URL = "https://monitoringapi.solaredge.com/site/%s/%s?api_key=%s";
    private static final String DETAILS = "details";
    private static final String OVERVIEW = "overview";

    public static InverterParametersResponse getInverterParameters(SolarEdgeInverter solarEdgeInverter) {
        try {
            JsonObject detailsResponse = getInverterDetailsResponse(solarEdgeInverter);
            JsonObject overviewResponse = getInverterOverviewResponse(solarEdgeInverter);
            return InverterParametersResponse.builder()
                    .status(getStatus(detailsResponse).name())
                    .currentPower(getCurrentPower(overviewResponse))
                    .todayYield(getTodayYield(overviewResponse))
                    .totalYield(getTotalYield(overviewResponse))
                    .build();
        } catch (Exception e) {
            log.error(e.toString());
        }
        return InverterParametersResponse.builder().status(Status.COMMUNICATION_ERROR.name()).build();

    }

    private static Double getTodayYield(JsonObject response) {
        return response.getAsJsonObject("overview").get("lastDayData").getAsJsonObject().get("energy").getAsDouble()/1000;
    }

    private static Double getTotalYield(JsonObject response) {
        String exponentialNotation = response.getAsJsonObject("overview").get("lifeTimeData").getAsJsonObject().get("energy").getAsString();
        Double base = Double.parseDouble(exponentialNotation.split("E")[0]);
        Double power = Double.parseDouble(exponentialNotation.split("E")[1]);
        return Math.pow(base, power)/1000;
    }

    private static Double getCurrentPower(JsonObject response) {
        return response.getAsJsonObject("overview").get("currentPower").getAsJsonObject().get("power").getAsDouble();
    }

    private static Status getStatus(JsonObject response) {
        switch (response.getAsJsonObject("details").get("status").getAsString()) {
            case "Active":
                return Status.ACTIVE;
            case "Pending":
                return Status.STANDBY;
            default:
                return Status.ALARM;
        }
    }

    private static JsonObject getInverterDetailsResponse(SolarEdgeInverter inverter) {
        return getInverterResponse(inverter, DETAILS);
    }

    private static JsonObject getInverterOverviewResponse(SolarEdgeInverter inverter) {
        return getInverterResponse(inverter, OVERVIEW);
    }

    private static JsonObject getInverterResponse(SolarEdgeInverter inverter, String parametersUrl) {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(String.format(URL, inverter.getSiteId(), parametersUrl, inverter.getApiKey()), String.class);
        return JsonParser.parseString(response).getAsJsonObject();
    }

}
