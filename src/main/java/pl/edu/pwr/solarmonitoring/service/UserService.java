package pl.edu.pwr.solarmonitoring.service;

import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.request.UserEditRequest;
import pl.edu.pwr.solarmonitoring.model.request.UserRequest;

public interface UserService {

    void createUser(UserRequest userRequest);
    void updateUser(User user, UserEditRequest userEditRequest);
    void deleteUser(User user);
}
