package pl.edu.pwr.solarmonitoring.service;

import pl.edu.pwr.solarmonitoring.model.request.UserRequest;

public interface UserService {

    void createUser(UserRequest userRequest);

}
