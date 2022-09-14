package pl.edu.pwr.solarmonitoring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.request.UserEditRequest;
import pl.edu.pwr.solarmonitoring.model.request.UserRequest;
import pl.edu.pwr.solarmonitoring.repository.UserRepository;
import pl.edu.pwr.solarmonitoring.service.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void createUser(UserRequest userRequest) {
        log.debug("Create user " + userRequest);
        if (userRequest.getUsername() == null || userRequest.getEmail() == null || userRequest.getPassword() == null) {
            String msg = "Empty field in user request";
            log.debug(msg);
            throw new IllegalArgumentException(msg);
        }
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            String msg = String.format("User with username: %s exists", userRequest.getUsername());
            log.debug(msg);
            throw new IllegalArgumentException(msg);
        }
        User newUser = User.builder()
                .username(userRequest.getUsername())
                .password(encoder.encode(userRequest.getPassword()))
                .email(userRequest.getEmail())
                .build();
        userRepository.save(newUser);
    }

    @Override
    public void updateUser(User user, UserEditRequest userEditRequest) {
        log.debug("Update user " + user + " " + userEditRequest);
        if (StringUtils.equals(user.getUsername(), userEditRequest.getUsername()) && userRepository.findByUsername(userEditRequest.getUsername()).isPresent()) {
            String msg = String.format("User with username: %s exists", userEditRequest.getUsername());
            log.debug(msg);
            throw new IllegalArgumentException(msg);
        }

        if (userEditRequest.getOldPassword() != null && StringUtils.equals(userEditRequest.getOldPassword(),userEditRequest.getNewPassword())) {
            String msg = "Old and new passwords are the same";
            log.debug(msg);
            throw new IllegalArgumentException(msg);
        }
        if (StringUtils.isNotEmpty(userEditRequest.getOldPassword()) && StringUtils.isNotEmpty(userEditRequest.getNewPassword())) {
            if (encoder.matches(userEditRequest.getOldPassword(), user.getPassword())) {
                user.setPassword(encoder.encode(userEditRequest.getNewPassword()));
            } else {
                String msg = "Invalid password";
                log.debug(msg);
                throw new IllegalArgumentException(msg);
            }
        }

        if (StringUtils.isNotEmpty(userEditRequest.getUsername())) {
            user.setUsername(userEditRequest.getUsername());
        }

        if (StringUtils.isNotEmpty(userEditRequest.getEmail())) {
            user.setEmail(userEditRequest.getEmail());
        }

        userRepository.save(user);
    }
}
