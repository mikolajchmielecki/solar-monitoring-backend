package pl.edu.pwr.solarmonitoring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.solarmonitoring.exchange.energa.EnergaExchange;
import pl.edu.pwr.solarmonitoring.utils.UserUtils;

@RestController
@RequestMapping("api/v1/energy")
@RequiredArgsConstructor
public class EnergyController {

    private final EnergaExchange energaExchange;

    @GetMapping("/before")
    public ResponseEntity getEnergyInBeforeMonth(Authentication authentication) {
        return new ResponseEntity(energaExchange.getEnergaDataForBeforeMonth(UserUtils.fromAuthentication(authentication)), HttpStatus.OK);
    }

}
