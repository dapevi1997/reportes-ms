package co.com.crediya.model.solicitud;
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
public class Solicitud {
    private String email;
    private BigDecimal monto;
    private Integer plazo;
    private String tipoPrestamo;
    private String estado;
    private BigDecimal totalMontoAprobadoUltimoMes;
    private BigDecimal tasaInteres;
    private String nombreUsuario;
    private BigDecimal salarioBase;
    private LocalDate fechaCreacion;
}
