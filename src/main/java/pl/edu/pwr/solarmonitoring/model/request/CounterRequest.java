package pl.edu.pwr.solarmonitoring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounterRequest {

    private String login;
    private String password;

}
