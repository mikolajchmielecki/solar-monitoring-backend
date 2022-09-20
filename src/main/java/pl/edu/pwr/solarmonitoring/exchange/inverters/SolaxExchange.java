package pl.edu.pwr.solarmonitoring.exchange.inverters;

import org.springframework.stereotype.Service;
import pl.edu.pwr.solarmonitoring.model.SolaxInverter;

@Service
public class SolaxExchange {

    private static final String URL = "https://www.solaxcloud.com:9443/proxy/api/getRealtimeInfo.do?tokenId=%s&sn=%s";

    public static Double getTodayYield(SolaxInverter inverter) {
        return null;
    }

    public static Double getCurrentPower(SolaxInverter inverter) {
        return null;
    }

    public static Status getStatus(SolaxInverter inverter) {
        return null;
    }

}
