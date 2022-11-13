package pl.edu.pwr.solarmonitoring.service;

import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.request.CounterRequest;
import pl.edu.pwr.solarmonitoring.model.response.CounterResponse;

public interface CounterService {
    void update(User user, CounterRequest counterRequest);

    CounterResponse get(User user);
}
