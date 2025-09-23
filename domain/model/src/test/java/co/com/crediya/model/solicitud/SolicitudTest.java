package co.com.crediya.model.solicitud;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SolicitudTest {

    @Test
    void testBuilderAndGetters() {
        LocalDate fecha = LocalDate.of(2025, 9, 19);

        Solicitud solicitud = Solicitud.builder()
                .email("test@crediya.com")
                .monto(BigDecimal.valueOf(10000))
                .plazo(12)
                .tipoPrestamo("Consumo")
                .estado("APROBADO")
                .totalMontoAprobadoUltimoMes(BigDecimal.valueOf(50000))
                .tasaInteres(BigDecimal.valueOf(1.5))
                .nombreUsuario("Juan Pérez")
                .salarioBase(BigDecimal.valueOf(2000))
                .fechaCreacion(fecha)
                .build();

        assertEquals("test@crediya.com", solicitud.getEmail());
        assertEquals(BigDecimal.valueOf(10000), solicitud.getMonto());
        assertEquals(12, solicitud.getPlazo());
        assertEquals("Consumo", solicitud.getTipoPrestamo());
        assertEquals("APROBADO", solicitud.getEstado());
        assertEquals(BigDecimal.valueOf(50000), solicitud.getTotalMontoAprobadoUltimoMes());
        assertEquals(BigDecimal.valueOf(1.5), solicitud.getTasaInteres());
        assertEquals("Juan Pérez", solicitud.getNombreUsuario());
        assertEquals(BigDecimal.valueOf(2000), solicitud.getSalarioBase());
        assertEquals(fecha, solicitud.getFechaCreacion());
    }

    @Test
    void testSettersAndNoArgsConstructor() {
        Solicitud solicitud = new Solicitud();
        solicitud.setEmail("otro@crediya.com");
        solicitud.setMonto(BigDecimal.TEN);

        assertEquals("otro@crediya.com", solicitud.getEmail());
        assertEquals(BigDecimal.TEN, solicitud.getMonto());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDate fecha = LocalDate.now();
        Solicitud solicitud = new Solicitud(
                "full@crediya.com",
                BigDecimal.ONE,
                6,
                "Hipotecario",
                "PENDIENTE",
                BigDecimal.TEN,
                BigDecimal.valueOf(2.0),
                "María López",
                BigDecimal.valueOf(3000),
                fecha
        );

        assertEquals("full@crediya.com", solicitud.getEmail());
        assertEquals(BigDecimal.ONE, solicitud.getMonto());
        assertEquals(6, solicitud.getPlazo());
        assertEquals("Hipotecario", solicitud.getTipoPrestamo());
        assertEquals("PENDIENTE", solicitud.getEstado());
        assertEquals(BigDecimal.TEN, solicitud.getTotalMontoAprobadoUltimoMes());
        assertEquals(BigDecimal.valueOf(2.0), solicitud.getTasaInteres());
        assertEquals("María López", solicitud.getNombreUsuario());
        assertEquals(BigDecimal.valueOf(3000), solicitud.getSalarioBase());
        assertEquals(fecha, solicitud.getFechaCreacion());
    }

    @Test
    void testToBuilder() {
        Solicitud original = Solicitud.builder()
                .email("original@crediya.com")
                .monto(BigDecimal.valueOf(1000))
                .plazo(12)
                .build();

        Solicitud copia = original.toBuilder()
                .monto(BigDecimal.valueOf(2000)) // cambiamos solo el monto
                .build();

        assertEquals("original@crediya.com", copia.getEmail()); // se conserva
        assertEquals(BigDecimal.valueOf(2000), copia.getMonto()); // se cambia
        assertEquals(12, copia.getPlazo()); // se conserva
    }
}
