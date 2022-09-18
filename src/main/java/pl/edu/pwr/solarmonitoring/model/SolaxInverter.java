package pl.edu.pwr.solarmonitoring.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "solax_inverters")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class SolaxInverter extends Inverter {

    @Column
    private String serialNumber;

    @Column
    private String tokenId;

}
