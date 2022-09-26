package pl.edu.pwr.solarmonitoring.model;

import lombok.Data;
import pl.edu.pwr.solarmonitoring.exchange.inverters.InverterParameters;

import javax.persistence.*;

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

}
