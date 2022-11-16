package pl.edu.pwr.solarmonitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.solarmonitoring.model.Counter;
import pl.edu.pwr.solarmonitoring.model.RemittedHistoryData;

import java.time.LocalDate;
import java.util.List;

public interface RemittedHistoryDataRepository extends JpaRepository<RemittedHistoryData, Long> {

    List<RemittedHistoryData> findRemittedHistoryDataByCounterAndDateBetweenOrderByDate(Counter counter, LocalDate start, LocalDate end);

}
