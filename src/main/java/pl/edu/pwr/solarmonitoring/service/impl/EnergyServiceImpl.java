package pl.edu.pwr.solarmonitoring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pwr.solarmonitoring.model.Inverter;
import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.mappers.HistoryDataMapper;
import pl.edu.pwr.solarmonitoring.model.response.ArchivedEnergyResponse;
import pl.edu.pwr.solarmonitoring.model.response.HistoryDataResponse;
import pl.edu.pwr.solarmonitoring.repository.ChargedHistoryDataRepository;
import pl.edu.pwr.solarmonitoring.repository.ProducedHistoryDataRepository;
import pl.edu.pwr.solarmonitoring.repository.RemittedHistoryDataRepository;
import pl.edu.pwr.solarmonitoring.service.EnergyService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class EnergyServiceImpl implements EnergyService {


    private final ProducedHistoryDataRepository producedHistoryDataRepository;
    private final ChargedHistoryDataRepository chargedHistoryDataRepository;
    private final RemittedHistoryDataRepository remittedHistoryDataRepository;

    @Override
    public ArchivedEnergyResponse getArchivedEnergy(User user, Integer year) {
        LocalDate startDate = LocalDate.of(year - 1, 12, 31);
        LocalDate endDate = LocalDate.of(year + 1, 1, 1);

        Collection<HistoryDataResponse> chargedHistoryData = chargedHistoryDataRepository
                .findChargedHistoryDataByCounterAndDateBetween(user.getCounter(), startDate, endDate)
                .stream()
                .map(d -> HistoryDataMapper.INSTANCE.fromChargedHistoryData(d))
                .collect(Collectors.toList());
        Collection<HistoryDataResponse> remittedHistoryData = remittedHistoryDataRepository
                .findRemittedHistoryDataByCounterAndDateBetween(user.getCounter(), startDate, endDate)
                .stream()
                .map(d -> HistoryDataMapper.INSTANCE.fromRemittedHistoryData(d))
                .collect(Collectors.toList());

        Map<LocalDate, HistoryDataResponse> totalProducedEnergyMap = new HashMap<>();
        Map<String, Collection<HistoryDataResponse>> producedHistoryDataMap = new HashMap<>();
        for (Inverter inverter : user.getInverters()) {
            Collection<HistoryDataResponse> producedEnergy = producedHistoryDataRepository
                    .findProducedHistoryDataByInverterAndDateBetween(inverter, startDate, endDate)
                    .stream()
                    .map(d -> HistoryDataMapper.INSTANCE.fromProducedHistoryData(d))
                    .collect(Collectors.toList());
            producedHistoryDataMap.put(inverter.getName(), producedEnergy);

            for (HistoryDataResponse data : producedEnergy) {
                if (!totalProducedEnergyMap.containsKey(data.getDate())) {
                    totalProducedEnergyMap.put(data.getDate(), HistoryDataResponse.builder()
                            .date(data.getDate())
                            .value(data.getValue())
                            .build());
                } else {
                    Double value = totalProducedEnergyMap.get(data.getDate()).getValue();
                    totalProducedEnergyMap.get(data.getDate()).setValue(value + data.getValue());
                }
            }
        }

        Collection<HistoryDataResponse> totalProducedEnergy = totalProducedEnergyMap.values();

        return ArchivedEnergyResponse.builder()
                .year(year)
                .chargeEnergy(chargedHistoryData)
                .remitEnergy(remittedHistoryData)
                .producedEnergy(producedHistoryDataMap)
                .totalProducedEnergy(totalProducedEnergy)
                .build();
    }
}
