package pl.edu.pwr.solarmonitoring.model;

import lombok.*;
import pl.edu.pwr.solarmonitoring.utils.EncryptionUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "counters")
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    @OneToMany
    private List<HistoryData> remitEnergy;

    @Column
    @OneToMany
    private List<HistoryData> chargeEnergy;

    public String getPassword() {
        return password != null ? EncryptionUtils.decrypt(password) : null;
    }

    public void setPassword(String password) {
        this.password = EncryptionUtils.encrypt(password);
    }

}
