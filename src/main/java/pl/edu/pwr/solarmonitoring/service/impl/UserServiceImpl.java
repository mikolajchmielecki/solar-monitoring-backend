package pl.edu.pwr.solarmonitoring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.request.UserEditRequest;
import pl.edu.pwr.solarmonitoring.model.request.UserRequest;
import pl.edu.pwr.solarmonitoring.model.response.UserResponse;
import pl.edu.pwr.solarmonitoring.repository.UserRepository;
import pl.edu.pwr.solarmonitoring.service.UserService;
import pl.edu.pwr.solarmonitoring.utils.EncryptionUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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
                .password(EncryptionUtils.PASSWORD_ENCODER.encode(userRequest.getPassword()))
                .email(userRequest.getEmail())
                .firstName(userRequest.getFirstName())
                .secondName(userRequest.getSecondName())
                .build();
        userRepository.save(newUser);
    }

    @Override
    public void updateUser(User user, UserEditRequest userEditRequest) {
        log.debug("Update user " + user + " " + userEditRequest);
        if (!StringUtils.equals(user.getUsername(), userEditRequest.getUsername()) && userRepository.findByUsername(userEditRequest.getUsername()).isPresent()) {
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
            if (EncryptionUtils.PASSWORD_ENCODER.matches(userEditRequest.getOldPassword(), user.getPassword())) {
                user.setPassword(EncryptionUtils.PASSWORD_ENCODER.encode(userEditRequest.getNewPassword()));
            } else {
                String msg = "Invalid password";
                log.debug(msg);
                throw new IllegalArgumentException(msg);
            }
        }

        if (StringUtils.isNotEmpty(userEditRequest.getUsername())) {
            user.setUsername(userEditRequest.getUsername());
        }

        if (StringUtils.isNotEmpty(userEditRequest.getFirstName())) {
            user.setFirstName(userEditRequest.getFirstName());
        }
        if (StringUtils.isNotEmpty(userEditRequest.getSecondName())) {
            user.setSecondName(userEditRequest.getSecondName());
        }

        if (StringUtils.isNotEmpty(userEditRequest.getEmail())) {
            user.setEmail(userEditRequest.getEmail());
        }

        userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        log.debug("Delete user " + user);
        userRepository.delete(user);
    }

    @Override
    public UserResponse getUser(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .email(user.getEmail())
                .build();
    }

}
