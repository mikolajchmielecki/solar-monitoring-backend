package pl.edu.pwr.solarmonitoring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequest {

    private String username;
    private String password;
    private String firstName;
    private String secondName;
    private String email;

}
