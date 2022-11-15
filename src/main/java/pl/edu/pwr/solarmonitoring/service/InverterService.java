package pl.edu.pwr.solarmonitoring.service;

import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.request.SolarEdgeRequest;
import pl.edu.pwr.solarmonitoring.model.request.SolaxRequest;
import pl.edu.pwr.solarmonitoring.model.response.InverterParametersResponse;
import pl.edu.pwr.solarmonitoring.model.response.InverterResponse;

import java.util.Set;

public interface InverterService {

    void add(User user, SolaxRequest request);
    void add(User user, SolarEdgeRequest request);
    void update(User user, SolaxRequest request);
    void update(User user, SolarEdgeRequest request);
    void delete(User user, Long id);
    InverterParametersResponse getParameters(User user, Long id);
    Set<InverterResponse> findAllInverters(User user);
    InverterResponse getInverter(User user, Long id);

}
