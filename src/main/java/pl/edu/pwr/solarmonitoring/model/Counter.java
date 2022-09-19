package pl.edu.pwr.solarmonitoring.model;

import lombok.*;
import pl.edu.pwr.solarmonitoring.utils.EncryptionUtils;

import javax.persistence.*;

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

    public String getPassword() {
        return password != null ? EncryptionUtils.decrypt(password) : null;
    }

    public void setPassword(String password) {
        this.password = EncryptionUtils.encrypt(password);
    }

}
