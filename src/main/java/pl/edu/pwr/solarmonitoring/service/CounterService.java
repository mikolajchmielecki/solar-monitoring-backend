package pl.edu.pwr.solarmonitoring.service;

import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.request.CounterRequest;

public interface CounterService {
    void update(User user, CounterRequest counterRequest);
}
