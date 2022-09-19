package pl.edu.pwr.solarmonitoring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.edu.pwr.solarmonitoring.model.Counter;
import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.request.CounterRequest;
import pl.edu.pwr.solarmonitoring.repository.UserRepository;
import pl.edu.pwr.solarmonitoring.service.CounterService;

@RequiredArgsConstructor
@Service
@Slf4j
public class CounterServiceImpl implements CounterService {

    private final UserRepository userRepository;

    @Override
    public void update(User user, CounterRequest counterRequest) {
        log.debug("Update " + counterRequest + " to " + user);
        Counter counter = user.getCounter() != null ? user.getCounter() : new Counter();

        if (StringUtils.isNotEmpty(counterRequest.getLogin())) {
            counter.setLogin(counterRequest.getLogin());
        }
        if (StringUtils.isNotEmpty(counterRequest.getPassword())) {
            counter.setPassword(counterRequest.getPassword());
        }

        user.setCounter(counter);
        userRepository.save(user);
    }

}
