package pl.edu.pwr.solarmonitoring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pwr.solarmonitoring.model.*;
import pl.edu.pwr.solarmonitoring.model.mappers.HistoryDataMapper;
import pl.edu.pwr.solarmonitoring.model.response.ArchivedEnergyResponse;
import pl.edu.pwr.solarmonitoring.model.response.HistoryDataResponse;
import pl.edu.pwr.solarmonitoring.repository.ChargedHistoryDataRepository;
import pl.edu.pwr.solarmonitoring.repository.ProducedHistoryDataRepository;
import pl.edu.pwr.solarmonitoring.repository.RemittedHistoryDataRepository;
import pl.edu.pwr.solarmonitoring.service.EnergyService;
import pl.edu.pwr.solarmonitoring.utils.EnergyServiceUtils;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class EnergyServiceImpl implements EnergyService {

    private final ProducedHistoryDataRepository producedHistoryDataRepository;
    private final ChargedHistoryDataRepository chargedHistoryDataRepository;
    private final RemittedHistoryDataRepository remittedHistoryDataRepository;

    @Override
    public List<ArchivedEnergyResponse> getArchivedEnergy(User user, Integer year) {
        LocalDate startDate = LocalDate.of(year - 1, 12, 31);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        List<ChargedHistoryData> chargedHistoryData = chargedHistoryDataRepository.findChargedHistoryDataByCounterAndDateBetweenOrderByDate(user.getCounter(), startDate, endDate);
        ArchivedEnergyResponse chargedEnergyResponse = EnergyServiceUtils.getChargedHistoryResponse(chargedHistoryData);
        List<RemittedHistoryData> remittedHistoryData = remittedHistoryDataRepository.findRemittedHistoryDataByCounterAndDateBetweenOrderByDate(user.getCounter(), startDate, endDate);
        ArchivedEnergyResponse remittedEnergyResponse = EnergyServiceUtils.getRemittedHistoryResponse(remittedHistoryData);

        List<ArchivedEnergyResponse> producedEnergyList = new ArrayList<>();
        for (Inverter inverter : user.getInverters()) {
            List<ProducedHistoryData> producedEnergy = producedHistoryDataRepository
                    .findProducedHistoryDataByInverterAndDateBetweenOrderByDate(inverter, startDate, endDate);
            ArchivedEnergyResponse response = EnergyServiceUtils.getProducedEnergyResponse(producedEnergy, inverter.getName());
            EnergyServiceUtils.roundList(response.getData());
            producedEnergyList.add(response);
        }

        ArchivedEnergyResponse producedSummary = EnergyServiceUtils.getProducedSummaryResponse(producedEnergyList.stream().map(e -> e.getData()).collect(Collectors.toList()));

        ArchivedEnergyResponse wornOutEnergy = EnergyServiceUtils.getWornOutEnergy(remittedEnergyResponse.getData(), producedSummary.getData());

        List<ArchivedEnergyResponse> result = new ArrayList<>();

        EnergyServiceUtils.roundList(chargedEnergyResponse.getData());
        EnergyServiceUtils.roundList(chargedEnergyResponse.getData());
        EnergyServiceUtils.roundList(remittedEnergyResponse.getData());
        EnergyServiceUtils.roundList(producedSummary.getData());
        EnergyServiceUtils.roundList(wornOutEnergy.getData());

        result.add(chargedEnergyResponse);
        result.add(remittedEnergyResponse);
        result.add(producedSummary);
        result.add(wornOutEnergy);
        result.addAll(producedEnergyList);
        return result;
    }

}
