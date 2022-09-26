package pl.edu.pwr.solarmonitoring.model;

import lombok.*;
import pl.edu.pwr.solarmonitoring.exchange.inverters.SolaxExchange;
import pl.edu.pwr.solarmonitoring.model.response.InverterParametersResponse;
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
    public InverterParametersResponse getInverterParameters() {
        return SolaxExchange.getInverterParameters(this);
    }
}
