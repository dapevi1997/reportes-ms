package co.com.crediya.model.reporte.excepciones;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExcepcionDominioTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        // Arrange
        String mensaje = "Error de dominio";

        // Act
        ExcepcionDominio excepcion = new ExcepcionDominio(mensaje);

        // Assert
        assertNotNull(excepcion);
        assertEquals(mensaje, excepcion.getMessage());
    }

    @Test
    void shouldBeInstanceOfRuntimeException() {
        // Arrange
        String mensaje = "Otro error";

        // Act
        ExcepcionDominio excepcion = new ExcepcionDominio(mensaje);

        // Assert
        assertTrue(excepcion instanceof RuntimeException);
    }
}
