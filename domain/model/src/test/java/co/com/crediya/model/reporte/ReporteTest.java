package co.com.crediya.model.reporte;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ReporteTest {

    @Test
    void shouldCreateWithNoArgsConstructorAndSetters() {
        // Arrange
        Reporte reporte = new Reporte();

        // Act
        reporte.setId("R1");
        reporte.setCantidadPrestamosAprobados(10L);
        reporte.setMontoPrestamosAprobados(BigDecimal.valueOf(5000));

        // Assert
        assertEquals("R1", reporte.getId());
        assertEquals(10L, reporte.getCantidadPrestamosAprobados());
        assertEquals(BigDecimal.valueOf(5000), reporte.getMontoPrestamosAprobados());
    }

    @Test
    void shouldCreateWithAllArgsConstructor() {
        // Arrange & Act
        Reporte reporte = new Reporte("R2", 20L, BigDecimal.valueOf(10000));

        // Assert
        assertEquals("R2", reporte.getId());
        assertEquals(20L, reporte.getCantidadPrestamosAprobados());
        assertEquals(BigDecimal.valueOf(10000), reporte.getMontoPrestamosAprobados());
    }

    @Test
    void shouldCreateWithBuilder() {
        // Arrange & Act
        Reporte reporte = Reporte.builder()
                .id("R3")
                .cantidadPrestamosAprobados(30L)
                .montoPrestamosAprobados(BigDecimal.valueOf(15000))
                .build();

        // Assert
        assertEquals("R3", reporte.getId());
        assertEquals(30L, reporte.getCantidadPrestamosAprobados());
        assertEquals(BigDecimal.valueOf(15000), reporte.getMontoPrestamosAprobados());
    }

    @Test
    void shouldCreateWithToBuilder() {
        // Arrange
        Reporte reporte = Reporte.builder()
                .id("R4")
                .cantidadPrestamosAprobados(40L)
                .montoPrestamosAprobados(BigDecimal.valueOf(20000))
                .build();

        // Act
        Reporte modificado = reporte.toBuilder()
                .montoPrestamosAprobados(BigDecimal.valueOf(25000))
                .build();

        // Assert
        assertEquals("R4", modificado.getId()); // mismo id
        assertEquals(40L, modificado.getCantidadPrestamosAprobados()); // misma cantidad
        assertEquals(BigDecimal.valueOf(25000), modificado.getMontoPrestamosAprobados()); // cambiado
    }
}
