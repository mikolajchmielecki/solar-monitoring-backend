package pl.edu.pwr.solarmonitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.solarmonitoring.model.ChargedHistoryData;
import pl.edu.pwr.solarmonitoring.model.Counter;
import pl.edu.pwr.solarmonitoring.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface ChargedHistoryDataRepository extends JpaRepository<ChargedHistoryData, Long> {

    Collection<ChargedHistoryData> findChargedHistoryDataByCounterAndDateBetween(Counter counter, LocalDate start, LocalDate end);

}
