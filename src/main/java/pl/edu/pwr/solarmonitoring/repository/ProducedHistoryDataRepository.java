package pl.edu.pwr.solarmonitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.solarmonitoring.model.Inverter;
import pl.edu.pwr.solarmonitoring.model.ProducedHistoryData;

import java.time.LocalDate;
import java.util.List;

public interface ProducedHistoryDataRepository extends JpaRepository<ProducedHistoryData, Long> {

    List<ProducedHistoryData> findProducedHistoryDataByInverterAndDateBetweenOrderByDate(Inverter inverter, LocalDate start, LocalDate end);

}
