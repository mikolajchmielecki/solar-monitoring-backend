package pl.edu.pwr.solarmonitoring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import pl.edu.pwr.solarmonitoring.utils.EncryptionUtils;

import javax.annotation.PostConstruct;

@Configuration
public class EncryptionUtilsConfig {

    @Value("${encryption.secret}")
    private String key;

    @PostConstruct
    public void init(){
        EncryptionUtils.setKey(key);
    }
}
