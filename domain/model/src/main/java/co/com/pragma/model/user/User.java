package co.com.pragma.model.user;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private Long id;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String phone;
    private String email;
    private Double baseSalary;

}
