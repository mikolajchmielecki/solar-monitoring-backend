package pl.edu.pwr.solarmonitoring.exchange.inverters;

public interface InverterParameters {

    Double getTodayYield();
    Double getCurrentPower();
    Status getStatus();

}
