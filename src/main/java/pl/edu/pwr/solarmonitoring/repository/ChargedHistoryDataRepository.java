package pl.edu.pwr.solarmonitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.solarmonitoring.model.ChargedHistoryData;
import pl.edu.pwr.solarmonitoring.model.Counter;

import java.time.LocalDate;
import java.util.List;

public interface ChargedHistoryDataRepository extends JpaRepository<ChargedHistoryData, Long> {

    List<ChargedHistoryData> findChargedHistoryDataByCounterAndDateBetweenOrderByDate(Counter counter, LocalDate start, LocalDate end);

}
