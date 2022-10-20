package pl.edu.pwr.solarmonitoring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.solarmonitoring.model.Inverter;
import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.request.SolarEdgeRequest;
import pl.edu.pwr.solarmonitoring.model.request.SolaxRequest;
import pl.edu.pwr.solarmonitoring.model.response.InverterResponse;
import pl.edu.pwr.solarmonitoring.service.InverterService;
import pl.edu.pwr.solarmonitoring.utils.UserUtils;

import java.util.Set;

@RestController
@RequestMapping(name = "/api/v1/inverter", produces= MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class InverterController {

    private final InverterService inverterService;

    @GetMapping("/all")
    public ResponseEntity<?> findAllInverters(Authentication authentication) {
        User user = UserUtils.fromAuthentication(authentication);
        try {
            return ResponseEntity.ok(inverterService.findAllInverters(user));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/solar-edge/add")
    public ResponseEntity<String> addSolarEdgeInverter(Authentication authentication, @RequestBody SolarEdgeRequest request) {
        User user = UserUtils.fromAuthentication(authentication);
        try {
            inverterService.add(user, request);
            return new ResponseEntity<>("Inverter " + request.toString() + " was added", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/solax/add")
    public ResponseEntity<String> addSolarEdgeInverter(Authentication authentication, @RequestBody SolaxRequest request) {
        User user = UserUtils.fromAuthentication(authentication);
        try {
            inverterService.add(user, request);
            return new ResponseEntity<>("Inverter " + request.toString() + " was added", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/solar-edge/update")
    public ResponseEntity<String> updateSolarEdgeInverter(Authentication authentication, @RequestBody SolarEdgeRequest request) {
        User user = UserUtils.fromAuthentication(authentication);
        try {
            inverterService.update(user, request);
            return new ResponseEntity<>("Inverter " + request.toString() + " was updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/solax/update")
    public ResponseEntity<String> updateSolarEdgeInverter(Authentication authentication, @RequestBody SolaxRequest request) {
        User user = UserUtils.fromAuthentication(authentication);
        try {
            inverterService.update(user, request);
            return new ResponseEntity<>("Inverter " + request.toString() + " was updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInverter(Authentication authentication, @PathVariable Long id) {
        User user = UserUtils.fromAuthentication(authentication);
        try {
            inverterService.delete(user, id);
            return new ResponseEntity<>("Inverter with id: " + id + " was deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/parameters/{id}")
    public ResponseEntity<?> getInverterParameters(Authentication authentication, @PathVariable Long id) {
        User user = UserUtils.fromAuthentication(authentication);
        try {
            return ResponseEntity.ok(inverterService.getParameters(user, id));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
