package pl.edu.pwr.solarmonitoring.model;


import lombok.*;
import pl.edu.pwr.solarmonitoring.exchange.inverters.SolaxExchange;
import pl.edu.pwr.solarmonitoring.exchange.inverters.Status;
import pl.edu.pwr.solarmonitoring.utils.EncryptionUtils;

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

    public String getTokenId() {
        return EncryptionUtils.decrypt(tokenId);
    }

    public void setTokenId(String tokenId) {
        this.tokenId = EncryptionUtils.encrypt(tokenId);
    }

    @Override
    public Double getTodayYield() {
        return SolaxExchange.getTodayYield(this);
    }

    @Override
    public Double getCurrentPower() {
        return SolaxExchange.getCurrentPower(this);
    }

    @Override
    public Status getStatus() {
        return SolaxExchange.getStatus(this);
    }
}
