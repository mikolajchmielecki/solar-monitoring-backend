package pl.edu.pwr.solarmonitoring.exchange.inverters;

import org.springframework.stereotype.Service;
import pl.edu.pwr.solarmonitoring.model.SolarEdgeInverter;

@Service
public class SolarEdgeExchange {

    private static final String URL = "https://www.solaxcloud.com:9443/proxy/api/getRealtimeInfo.do?tokenId=%s&sn=%s";


    public static Double getTodayYield(SolarEdgeInverter inverter) {
        return null;
    }

    public static Double getCurrentPower(SolarEdgeInverter inverter) {
        return null;
    }

    public static Status getStatus(SolarEdgeInverter inverter) {
        return null;
    }
}
