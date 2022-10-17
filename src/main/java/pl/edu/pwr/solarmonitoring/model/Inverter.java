package pl.edu.pwr.solarmonitoring.model;

import lombok.Data;
import lombok.ToString;
import pl.edu.pwr.solarmonitoring.exchange.inverters.InverterParameters;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "inverters")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@ToString
public abstract class Inverter implements InverterParameters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, mappedBy = "inverter")
    private List<ProducedHistoryData> producedEnergy;

    @Column
    private Double beforeEnergy;
}
