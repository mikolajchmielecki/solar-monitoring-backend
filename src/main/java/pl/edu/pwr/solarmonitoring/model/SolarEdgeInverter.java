package pl.edu.pwr.solarmonitoring.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "solar_edge_inverters")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class SolarEdgeInverter extends Inverter {

    @Column(nullable = false)
    private String apiKey;

    @Column(nullable = false)
    private String siteId;

}
