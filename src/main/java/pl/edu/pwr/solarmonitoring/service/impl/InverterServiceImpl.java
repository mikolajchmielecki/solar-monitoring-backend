package pl.edu.pwr.solarmonitoring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pwr.solarmonitoring.exchange.inverters.Status;
import pl.edu.pwr.solarmonitoring.model.Inverter;
import pl.edu.pwr.solarmonitoring.model.SolarEdgeInverter;
import pl.edu.pwr.solarmonitoring.model.SolaxInverter;
import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.mappers.InverterMapper;
import pl.edu.pwr.solarmonitoring.model.mappers.SolarEdgeInverterMapper;
import pl.edu.pwr.solarmonitoring.model.mappers.SolaxInverterMapper;
import pl.edu.pwr.solarmonitoring.model.request.SolarEdgeRequest;
import pl.edu.pwr.solarmonitoring.model.request.SolaxRequest;
import pl.edu.pwr.solarmonitoring.model.response.InverterParametersResponse;
import pl.edu.pwr.solarmonitoring.model.response.InverterResponse;
import pl.edu.pwr.solarmonitoring.repository.InverterRepository;
import pl.edu.pwr.solarmonitoring.repository.UserRepository;
import pl.edu.pwr.solarmonitoring.service.InverterService;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InverterServiceImpl implements InverterService {

    private final InverterRepository<SolaxInverter> solaxRepository;
    private final InverterRepository<SolarEdgeInverter> solarEdgeRepository;
    private final UserRepository userRepository;

    @Override
    public void add(User user, SolaxRequest request) {
        log.debug("Add " + request + " to " + user);
        if (request.getId() != null) {
            String msg = "Inverter request contains id";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        SolaxInverter inverter = SolaxInverterMapper.INSTANCE.requestToEntity(request);
        inverter.setTokenId(inverter.getTokenId());
        user.getInverters().add(inverter);
        userRepository.save(user);
    }

    @Override
    public void add(User user, SolarEdgeRequest request) {
        log.debug("Add " + request + " to " + user);
        if (request.getId() != null) {
            String msg = "Inverter request contains id";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        SolarEdgeInverter inverter = SolarEdgeInverterMapper.INSTANCE.requestToEntity(request);
        inverter.setApiKey(inverter.getApiKey());
        user.getInverters().add(inverter);
        userRepository.save(user);
    }

    @Override
    public void update(User user, SolaxRequest request) {
        log.debug("Delete " + request + " from " + user);
        if (request.getId() == null) {
            String msg = "Inverter request without id";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }

        SolaxInverter inverter = (SolaxInverter) getInverterFromUser(user, request.getId());

        if (request.getName() != null) {
            inverter.setName(request.getName());
        }
        if (request.getSerialNumber() != null) {
            inverter.setSerialNumber(request.getSerialNumber());
        }
        if (request.getTokenId() != null) {
            inverter.setTokenId(request.getTokenId());
        }

        solaxRepository.save(inverter);
    }

    @Override
    public void update(User user, SolarEdgeRequest request) {
        log.debug("Delete " + request + " from " + user);
        if (request.getId() == null) {
            String msg = "Inverter request without id";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }

        SolarEdgeInverter inverter = (SolarEdgeInverter) getInverterFromUser(user, request.getId());

        if (request.getName() != null) {
            inverter.setName(request.getName());
        }
        if (request.getSiteId() != null) {
            inverter.setSiteId(request.getSiteId());
        }
        if (request.getApiKey() != null) {
            inverter.setApiKey(request.getApiKey());
        }

        solarEdgeRepository.save(inverter);
    }

    @Override
    public void delete(User user, Long id) {
        log.debug("Delete inverter with id: " + id + " from " + user);
        Inverter inverterToRemove = getInverterFromUser(user, id);
        user.getInverters().remove(inverterToRemove);
        userRepository.save(user);
    }

    @Override
    public InverterParametersResponse getParameters(User user, Long id) {
        Inverter inverter = getInverterFromUser(user, id);
        InverterParametersResponse response = inverter.getInverterParameters();
        log.debug("getParameters " + response);
        return response;
    }

    @Override
    public InverterResponse getInverter(User user, Long id) {
        Inverter inverter = getInverterFromUser(user, id);
        InverterResponse response = InverterResponse.builder()
                .id(inverter.getId())
                .name(inverter.getName())
                .credentials(inverter.getCredentials())
                .type(inverter.getType())
                .build();
        return response;
    }

    @Override
    public Set<InverterResponse> findAllInverters(User user) {
        return user.getInverters().stream().map(i -> InverterMapper.INSTANCE.entityToResponse(i)).collect(Collectors.toSet());
    }

    private Inverter getInverterFromUser(User user, Long id) {
        Set<Inverter> inverters = user.getInverters();
        Optional<Inverter> inverter = inverters.stream()
                .filter(i -> i.getId().equals(id))
                .findAny();
        if (!inverter.isPresent()) {
            String msg = "User hasn't inverter with id: " + id;
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
        return inverter.get();
    }
}
