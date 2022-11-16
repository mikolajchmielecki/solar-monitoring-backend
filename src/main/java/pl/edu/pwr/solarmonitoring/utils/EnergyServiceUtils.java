package pl.edu.pwr.solarmonitoring.utils;

import pl.edu.pwr.solarmonitoring.model.ChargedHistoryData;
import pl.edu.pwr.solarmonitoring.model.ProducedHistoryData;
import pl.edu.pwr.solarmonitoring.model.RemittedHistoryData;
import pl.edu.pwr.solarmonitoring.model.response.ArchivedEnergyResponse;

import java.util.ArrayList;
import java.util.List;

public class EnergyServiceUtils {

    public static ArchivedEnergyResponse getChargedHistoryResponse(List<ChargedHistoryData> chargedHistoryData) {
        List<Double> result = getZeros(12);
        chargedHistoryData.stream().forEach(e -> {
            result.set(e.getDate().getMonthValue()-1, e.getValue());
        });
        return ArchivedEnergyResponse.builder()
                .name("Energia pobrana z sieci")
                .data(result)
                .build();
    }

    public static ArchivedEnergyResponse getRemittedHistoryResponse(List<RemittedHistoryData> remittedHistoryData) {
        List<Double> result = getZeros(12);
        remittedHistoryData.stream().forEach(e -> {
            result.set(e.getDate().getMonthValue()-1, e.getValue());
        });
        return ArchivedEnergyResponse.builder()
                .name("Energia oddana do sieci")
                .data(result)
                .build();
    }

    public static ArchivedEnergyResponse getProducedEnergyResponse(List<ProducedHistoryData> producedHistoryData, String name) {
        List<Double> result = getZeros(12);
        producedHistoryData.stream().forEach(e -> {
            result.set(e.getDate().getMonthValue()-1, e.getValue());
        });
        return ArchivedEnergyResponse.builder()
                .name("Produkcja " + name)
                .data(result)
                .build();
    }

    public static ArchivedEnergyResponse getProducedSummaryResponse(List<List<Double>> producedHistoryDatas) {
        List<Double> result = getZeros(12);
        for (List<Double> producedHistoryData : producedHistoryDatas) {
            for (int i = 0; i < producedHistoryData.size(); i++) {
                result.set(i, result.get(i) + producedHistoryData.get(i));
            }
        }
        return ArchivedEnergyResponse.builder()
                .name("Sumaryczna produkcja")
                .data(result)
                .build();
    }

    public static ArchivedEnergyResponse getWornOutEnergy(List<Double> remittedEnergy, List<Double> producedEnergy) {
        List<Double> result = getZeros(12);
        for (int i = 0; i < remittedEnergy.size(); i++) {
            result.set(i, producedEnergy.get(i) - remittedEnergy.get(i));
        }
        return ArchivedEnergyResponse.builder()
                .name("Energia zużyta bezpośrednio")
                .data(result)
                .build();
    }

    public static void roundList(List<Double> data) {
        for (int i = 0; i < data.size(); i++) {
            data.set(i, round(data.get(i)));
        }
    }

    static private Double round(Double number) {
        return Math.round(number * 100.0) / 100.0;
    }

    static private List<Double> getZeros(int size) {
        List<Double> result = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            result.add(0.0);
        }
        return result;
    }

}
