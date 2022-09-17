package pl.edu.pwr.solarmonitoring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserEditRequest {

    private String username;
    private String newPassword;
    private String oldPassword;
    private String email;

}
