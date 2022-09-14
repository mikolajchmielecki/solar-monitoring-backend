package pl.edu.pwr.solarmonitoring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.request.UserEditRequest;
import pl.edu.pwr.solarmonitoring.model.request.UserRequest;
import pl.edu.pwr.solarmonitoring.service.UserService;
import pl.edu.pwr.solarmonitoring.utils.UserUtils;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
        try {
            userService.createUser(userRequest);
            return new ResponseEntity<>(String.format("User %s created", userRequest.getUsername()), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateUser(Authentication authentication, @RequestBody UserEditRequest userEditRequest) {
        try {
            User user = UserUtils.fromAuthentication(authentication);
            userService.updateUser(user, userEditRequest);
            return new ResponseEntity<>(String.format("User %s updated", user.getUsername()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(Authentication authentication) {
        try {
            User user = UserUtils.fromAuthentication(authentication);
            userService.deleteUser(user);
            return new ResponseEntity<>(String.format("User %s deleted", user.getUsername()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
