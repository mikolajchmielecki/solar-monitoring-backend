package pl.edu.pwr.solarmonitoring.exchange.inverters;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.edu.pwr.solarmonitoring.model.SolarEdgeInverter;

@Service
public class SolarEdgeExchange {

    private static final String URL = "https://www.solaxcloud.com:9443/proxy/api/getRealtimeInfo.do?tokenId=%s&sn=%s";


    public static Double getTodayYield(SolarEdgeInverter inverter) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(URL, String.class);

        System.out.println(result);
        return null;
    }

    public static Double getCurrentPower(SolarEdgeInverter inverter) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(URL, String.class);

        System.out.println(result);
        return null;
    }

    public static Status getStatus(SolarEdgeInverter inverter) {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(URL, String.class);

        System.out.println(result);
        return null;
    }
}
