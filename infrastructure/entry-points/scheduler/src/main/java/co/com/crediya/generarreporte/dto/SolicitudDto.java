package co.com.crediya.generarreporte.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder(toBuilder = true)
public class SolicitudDto {
    private String email;
    private BigDecimal monto;
    private String estado;
    private LocalDate fechaCreacion;
}
