package pl.edu.pwr.solarmonitoring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.solarmonitoring.exchange.energa.EnergaExchange;
import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.utils.UserUtils;

@RestController
@RequestMapping("api/v1/energy")
@RequiredArgsConstructor
public class EnergyController {

    private final EnergaExchange energaExchange;

    @GetMapping("energa")
    public String getEnerga(Authentication authentication) throws InterruptedException {
        User user = UserUtils.fromAuthentication(authentication);
        return energaExchange.getEnergaData(user).toString();
    }
}
