package pl.edu.pwr.solarmonitoring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.request.UserRequest;
import pl.edu.pwr.solarmonitoring.repository.UserRepository;
import pl.edu.pwr.solarmonitoring.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void createUser(UserRequest userRequest) {
        if (userRequest.getUsername() == null || userRequest.getEmail() == null || userRequest.getPassword() == null) {
            throw new IllegalArgumentException("Empty field in user request");
        }
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException(String.format("User with username: %s exists", userRequest.getUsername()));
        }
        User newUser = User.builder()
                .username(userRequest.getUsername())
                .password(encoder.encode(userRequest.getPassword()))
                .email(userRequest.getEmail())
                .build();
        userRepository.save(newUser);
    }
}
