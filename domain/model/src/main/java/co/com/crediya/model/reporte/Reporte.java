package co.com.crediya.model.reporte;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Reporte {
    private String id;
    private Long cantidadPrestamosAprobados;
    private BigDecimal montoPrestamosAprobados;
}
