package pl.edu.pwr.solarmonitoring.utils;

import org.springframework.security.core.Authentication;
import pl.edu.pwr.solarmonitoring.model.User;

public class UserUtils {

    public static User fromAuthentication(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }
}
