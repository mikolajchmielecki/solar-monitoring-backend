package pl.edu.pwr.solarmonitoring.service;

import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.response.ArchivedEnergyResponse;

import java.util.List;

public interface EnergyService {
    List<ArchivedEnergyResponse> getArchivedEnergy(User user, Integer year);
}
