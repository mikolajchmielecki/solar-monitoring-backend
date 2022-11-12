package pl.edu.pwr.solarmonitoring.model.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserResponse {

    private String username;
    private String firstName;
    private String secondName;
    private String email;

}
