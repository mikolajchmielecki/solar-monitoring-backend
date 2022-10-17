package pl.edu.pwr.solarmonitoring.service;

import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.response.ArchivedEnergyResponse;

public interface EnergyService {
    ArchivedEnergyResponse getArchivedEnergy(User user, Integer year);
}
