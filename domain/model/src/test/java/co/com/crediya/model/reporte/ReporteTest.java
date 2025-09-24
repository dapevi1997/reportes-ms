package co.com.crediya.model.reporte;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ReporteTest {

    @Test
    void shouldCreateWithNoArgsConstructorAndSetters() {
        Reporte reporte = new Reporte();

        reporte.setId("R1");
        reporte.setCantidadPrestamosAprobados(10L);
        reporte.setMontoPrestamosAprobados(BigDecimal.valueOf(5000));

        assertEquals("R1", reporte.getId());
        assertEquals(10L, reporte.getCantidadPrestamosAprobados());
        assertEquals(BigDecimal.valueOf(5000), reporte.getMontoPrestamosAprobados());
    }

    @Test
    void shouldCreateWithAllArgsConstructor() {
        Reporte reporte = new Reporte("R2", 20L, BigDecimal.valueOf(10000));

        assertEquals("R2", reporte.getId());
        assertEquals(20L, reporte.getCantidadPrestamosAprobados());
        assertEquals(BigDecimal.valueOf(10000), reporte.getMontoPrestamosAprobados());
    }

    @Test
    void shouldCreateWithBuilder() {
        Reporte reporte = Reporte.builder()
                .id("R3")
                .cantidadPrestamosAprobados(30L)
                .montoPrestamosAprobados(BigDecimal.valueOf(15000))
                .build();

        assertEquals("R3", reporte.getId());
        assertEquals(30L, reporte.getCantidadPrestamosAprobados());
        assertEquals(BigDecimal.valueOf(15000), reporte.getMontoPrestamosAprobados());
    }

    @Test
    void shouldCreateWithToBuilder() {
        Reporte reporte = Reporte.builder()
                .id("R4")
                .cantidadPrestamosAprobados(40L)
                .montoPrestamosAprobados(BigDecimal.valueOf(20000))
                .build();

        Reporte modificado = reporte.toBuilder()
                .montoPrestamosAprobados(BigDecimal.valueOf(25000))
                .build();

        assertEquals("R4", modificado.getId());
        assertEquals(40L, modificado.getCantidadPrestamosAprobados());
        assertEquals(BigDecimal.valueOf(25000), modificado.getMontoPrestamosAprobados());
    }
}
