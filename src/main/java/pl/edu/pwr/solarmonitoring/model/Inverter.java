package pl.edu.pwr.solarmonitoring.model;

import lombok.Data;
import pl.edu.pwr.solarmonitoring.exchange.inverters.InverterParameters;
import pl.edu.pwr.solarmonitoring.model.response.InverterParametersResponse;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "inverters")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Inverter implements InverterParameters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
    private List<HistoryData> producedEnergy;

    @Column
    private Double beforeEnergy;
}
