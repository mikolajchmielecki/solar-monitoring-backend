package pl.edu.pwr.solarmonitoring.exchange.inverters;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;;
import pl.edu.pwr.solarmonitoring.model.SolaxInverter;

@Service
public class SolaxExchange {

    private static final String URL = "https://www.solaxcloud.com:9443/proxy/api/getRealtimeInfo.do?tokenId=%s&sn=%s";

    public static Double getTodayYield(SolaxInverter inverter) {
        JsonObject response = getInverterResponse(inverter);
        return response.getAsJsonObject("result").get("yieldtoday").getAsDouble();
    }

    public static Double getTotalYield(SolaxInverter inverter) {
        JsonObject response = getInverterResponse(inverter);
        return response.getAsJsonObject("result").get("yieldtotal").getAsDouble();
    }

    public static Double getCurrentPower(SolaxInverter inverter) {
        JsonObject response = getInverterResponse(inverter);
        return response.getAsJsonObject("result").get("acpower").getAsDouble();
    }

    public static Status getStatus(SolaxInverter inverter) {
        try {
            JsonObject response = getInverterResponse(inverter);
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
