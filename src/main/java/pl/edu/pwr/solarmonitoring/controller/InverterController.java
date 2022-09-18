package pl.edu.pwr.solarmonitoring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.request.SolarEdgeRequest;
import pl.edu.pwr.solarmonitoring.model.request.SolaxRequest;
import pl.edu.pwr.solarmonitoring.service.InverterService;
import pl.edu.pwr.solarmonitoring.utils.UserUtils;

@RestController
@RequestMapping("/api/v1/inverter")
@RequiredArgsConstructor
public class InverterController {

    private final InverterService inverterService;

    @PostMapping("/solax/add")
    public ResponseEntity<String> addSolarEdgeInverter(Authentication authentication, @RequestBody SolarEdgeRequest request) {
        User user = UserUtils.fromAuthentication(authentication);
        try {
            inverterService.add(user, request);
            return new ResponseEntity<>("Inverter " + request.toString() + " was added", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/solar-edge/add")
    public ResponseEntity<String> addSolarEdgeInverter(Authentication authentication, @RequestBody SolaxRequest request) {
        User user = UserUtils.fromAuthentication(authentication);
        try {
            inverterService.add(user, request);
            return new ResponseEntity<>("Inverter " + request.toString() + " was added", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/solax/edit")
    public ResponseEntity<String> updateSolarEdgeInverter(Authentication authentication, @RequestBody SolarEdgeRequest request) {
        User user = UserUtils.fromAuthentication(authentication);
        try {
            inverterService.update(user, request);
            return new ResponseEntity<>("Inverter " + request.toString() + " was updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/solar-edge/edit")
    public ResponseEntity<String> updateSolarEdgeInverter(Authentication authentication, @RequestBody SolaxRequest request) {
        User user = UserUtils.fromAuthentication(authentication);
        try {
            inverterService.update(user, request);
            return new ResponseEntity<>("Inverter " + request.toString() + " was updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/solax/delete")
    public ResponseEntity<String> deleteSolarEdgeInverter(Authentication authentication, @RequestBody SolarEdgeRequest request) {
        User user = UserUtils.fromAuthentication(authentication);
        try {
            inverterService.delete(user, request);
            return new ResponseEntity<>("Inverter " + request.toString() + " was deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/solar-edge/delete")
    public ResponseEntity<String> deleteSolarEdgeInverter(Authentication authentication, @RequestBody SolaxRequest request) {
        User user = UserUtils.fromAuthentication(authentication);
        try {
            inverterService.delete(user, request);
            return new ResponseEntity<>("Inverter " + request.toString() + " was deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
