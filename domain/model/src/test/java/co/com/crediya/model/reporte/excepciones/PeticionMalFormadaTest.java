package co.com.crediya.model.reporte.excepciones;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PeticionMalFormadaTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        // Arrange
        String mensaje = "La petición está mal formada";

        // Act
        PeticionMalFormada exception = new PeticionMalFormada(mensaje);

        // Assert
        assertNotNull(exception);
        assertEquals(mensaje, exception.getMessage());
    }

    @Test
    void shouldBeInstanceOfRuntimeException() {
        // Arrange
        String mensaje = "Error en petición";

        // Act
        PeticionMalFormada exception = new PeticionMalFormada(mensaje);

        // Assert
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void shouldBeThrownAndCaught() {
        // Arrange
        String mensaje = "Petición inválida";

        // Act & Assert
        try {
            throw new PeticionMalFormada(mensaje);
        } catch (PeticionMalFormada e) {
            assertEquals(mensaje, e.getMessage());
        }
    }
}
