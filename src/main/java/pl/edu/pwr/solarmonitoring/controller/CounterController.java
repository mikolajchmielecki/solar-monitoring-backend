package pl.edu.pwr.solarmonitoring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.request.CounterRequest;
import pl.edu.pwr.solarmonitoring.service.CounterService;
import pl.edu.pwr.solarmonitoring.utils.UserUtils;

@RestController
@RequestMapping("/api/v1/counter")
@RequiredArgsConstructor
public class CounterController {

    private final CounterService counterService;

    @PatchMapping("update")
    public ResponseEntity<String> updateCounter(Authentication authentication, @RequestBody CounterRequest counterRequest) {
        User user = UserUtils.fromAuthentication(authentication);
        try {
            counterService.update(user, counterRequest);
            return new ResponseEntity<>("Counter " + counterRequest + " was updated.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
