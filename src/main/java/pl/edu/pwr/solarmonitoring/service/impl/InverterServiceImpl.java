package pl.edu.pwr.solarmonitoring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class InverterServiceImpl implements InverterService {

    private final InverterRepository<SolaxInverter> solaxRepository;
    private final InverterRepository<SolarEdgeInverter> solarEdgeRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void add(User user, SolaxRequest request) {
        SolaxInverter inverter = SolaxInverterMapper.INSTANCE.requestToEntity(request);
        user.getInverters().add(inverter);
        userRepository.save(user);
        solaxRepository.save(inverter);
    }

    @Override
    @Transactional
    public void add(User user, SolarEdgeRequest request) {
        SolarEdgeInverter inverter = SolarEdgeInverterMapper.INSTANCE.requestToEntity(request);
        user.getInverters().add(inverter);
        userRepository.save(user);
        solarEdgeRepository.save(inverter);
    }

    @Override
    public void update(User user, SolaxRequest request) {

    }

    @Override
    public void update(User user, SolarEdgeRequest request) {

    }

    @Override
    public void delete(User user, SolaxRequest request) {

    }

    @Override
    public void delete(User user, SolarEdgeRequest request) {

    }
}
