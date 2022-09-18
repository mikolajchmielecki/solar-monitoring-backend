package pl.edu.pwr.solarmonitoring.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "inverters")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Inverter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    public String name;

}
