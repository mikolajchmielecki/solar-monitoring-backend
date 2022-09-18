package pl.edu.pwr.solarmonitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pwr.solarmonitoring.model.Inverter;

public interface InverterRepository<T extends Inverter> extends JpaRepository<T, Long> {
}
