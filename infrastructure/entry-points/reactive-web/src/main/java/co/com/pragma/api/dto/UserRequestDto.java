package co.com.pragma.api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "Los nombres son obligatorios")
    private String name;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String lastName;

    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate birthDate;

    private String address;

    private String phone;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico no tiene un formato válido")
    private String email;

    @NotNull(message = "El salario base es obligatorio")
    @Min(value = 0, message = "El salario base debe ser mayor o igual a 0")
    @Max(value = 15000000, message = "El salario base no puede superar los 15 millones")
    private Double baseSalary;

}
