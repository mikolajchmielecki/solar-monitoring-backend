package pl.edu.pwr.solarmonitoring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pwr.solarmonitoring.model.Inverter;
import pl.edu.pwr.solarmonitoring.model.SolarEdgeInverter;
import pl.edu.pwr.solarmonitoring.model.SolaxInverter;
import pl.edu.pwr.solarmonitoring.model.User;
import pl.edu.pwr.solarmonitoring.model.mappers.SolarEdgeInverterMapper;
import pl.edu.pwr.solarmonitoring.model.mappers.SolaxInverterMapper;
import pl.edu.pwr.solarmonitoring.model.request.SolarEdgeRequest;
import pl.edu.pwr.solarmonitoring.model.request.SolaxRequest;
import pl.edu.pwr.solarmonitoring.repository.InverterRepository;
import pl.edu.pwr.solarmonitoring.repository.UserRepository;
import pl.edu.pwr.solarmonitoring.service.InverterService;

import java.util.Optional;
import java.util.Set;

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
        SolaxInverter inverter = SolaxInverterMapper.INSTANCE.requestToEntity(request);
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

        Optional<Inverter> inverter = getInverterFromUser(user, request.getId());
        if (!inverter.isPresent()) {
            String msg = user + " hasn't that inverter";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }

        SolaxInverter solaxInverter = (SolaxInverter) inverter.get();
        if (request.getName() != null) {
            solaxInverter.setName(request.getName());
        }
        if (request.getSerialNumber() != null) {
            solaxInverter.setSerialNumber(request.getSerialNumber());
        }
        if (request.getTokenId() != null) {
            solaxInverter.setTokenId(request.getTokenId());
        }

        solaxRepository.save(solaxInverter);
    }

    @Override
    public void update(User user, SolarEdgeRequest request) {
        log.debug("Delete " + request + " from " + user);
        if (request.getId() == null) {
            String msg = "Inverter request without id";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }

        Optional<Inverter> inverter = getInverterFromUser(user, request.getId());
        if (!inverter.isPresent()) {
            String msg = user + " hasn't that inverter";
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }

        SolarEdgeInverter solaxInverter = (SolarEdgeInverter) inverter.get();
        if (request.getName() != null) {
            solaxInverter.setName(request.getName());
        }
        if (request.getSiteId() != null) {
            solaxInverter.setSiteId(request.getSiteId());
        }
        if (request.getSiteId() != null) {
            solaxInverter.setApiKey(request.getSiteId());
        }

        solarEdgeRepository.save(solaxInverter);
    }

    @Override
    public void delete(User user, Long id) {
        log.debug("Delete inverter with id: " + id + " from " + user);
        Optional<Inverter> inverterToRemove = getInverterFromUser(user, id);
        if (inverterToRemove.isPresent()) {
            user.getInverters().remove(inverterToRemove.get());
        } else {
            String msg = "User hasn't inverter with id: " + id;
            log.error(msg);
            throw new IllegalArgumentException();
        }
        userRepository.save(user);
    }

    private Optional<Inverter> getInverterFromUser(User user, Long id) {
        Set<Inverter> inverters = user.getInverters();
        Optional<Inverter> inverter = inverters.stream()
                .filter(i -> i.getId().equals(id))
                .findAny();
        return inverter;
    }
}
