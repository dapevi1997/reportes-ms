package co.com.crediya.model.usuario;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Usuario {
    private Long idUser;
    private String name;
    private String lastName;
    private String email;
    private LocalDate birthday;
    private String address;
    private String documentId;
    private String phone;
    private BigDecimal baseSalary;
    private Long idRole;
}
