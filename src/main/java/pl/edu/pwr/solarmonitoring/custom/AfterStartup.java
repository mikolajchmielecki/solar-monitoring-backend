package pl.edu.pwr.solarmonitoring.custom;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AfterStartup {


    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
    }
}
