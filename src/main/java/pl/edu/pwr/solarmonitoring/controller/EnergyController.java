package pl.edu.pwr.solarmonitoring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.solarmonitoring.scheduling.ScheduledTasks;

@RestController
@RequestMapping("api/v1/energy")
@RequiredArgsConstructor
public class EnergyController {

    private final ScheduledTasks scheduledTasks;

    @GetMapping("/run")
    public ResponseEntity getEnergyInBeforeMonth() {
        scheduledTasks.saveMonthlyEnergaData();
        scheduledTasks.saveMonthlyInvertersData();
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/get/{year}")
    public ResponseEntity getArchivedEnergy(Authentication authentication, @PathVariable Integer year) {
        return new ResponseEntity(HttpStatus.OK);
    }

}
