package co.com.crediya.model.reporte;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConstantesTest {

    @Test
    void shouldReturnCorrectIdReportesCrediya() {
        // Arrange & Act
        String result = Constantes.ID_REPORTES_CREDIYA;

        // Assert
        assertEquals("id_reportes", result);
    }

    @Test
    void shouldReturnCorrectMensajeReporteNoEncontradoConId() {
        // Arrange & Act
        String result = Constantes.MENSAJE_REPORTE_NO_ENCONTRADO_CON_ID;

        // Assert
        assertEquals("Reporte no encontrado con id: ", result);
    }

    @Test
    void shouldReturnCorrectReporteEncontrado() {
        // Arrange & Act
        String result = Constantes.REPORTE_ENCONTRADO;

        // Assert
        assertEquals("Reporte encontrado con id", result);
    }

    @Test
    void shouldReturnCorrectReporteNoEncontrado() {
        // Arrange & Act
        String result = Constantes.REPORTE_NO_ENCONTRADO;

        // Assert
        assertEquals("Reporte no encontrado con id", result);
    }

    @Test
    void shouldReturnCorrectErrorDeserializando() {
        // Arrange & Act
        String result = Constantes.ERROR_DESERIALIZANDO;

        // Assert
        assertEquals("Error deserializando mensaje entrante", result);
    }
}
