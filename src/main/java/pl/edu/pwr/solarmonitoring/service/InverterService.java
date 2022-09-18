package pl.edu.pwr.solarmonitoring.service;

import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.request.SolarEdgeRequest;
import pl.edu.pwr.solarmonitoring.model.request.SolaxRequest;

public interface InverterService {

    void add(User user, SolaxRequest request);
    void add(User user, SolarEdgeRequest request);
    void update(User user, SolaxRequest request);
    void update(User user, SolarEdgeRequest request);
    void delete(User user, SolaxRequest request);
    void delete(User user, SolarEdgeRequest request);

}
