package pl.edu.pwr.solarmonitoring.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pl.edu.pwr.solarmonitoring.exchange.inverters.InverterParameters;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "inverters")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@ToString
@EqualsAndHashCode
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

    public abstract String getType();

    public abstract Map<String, String> getCredentials();
}
