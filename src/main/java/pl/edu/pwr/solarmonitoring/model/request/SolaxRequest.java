package pl.edu.pwr.solarmonitoring.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class SolaxRequest {

    private Long id;
    private String name;
    private String serialNumber;
    private String tokenId;

}
