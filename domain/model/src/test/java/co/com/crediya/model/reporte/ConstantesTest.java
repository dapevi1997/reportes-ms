package co.com.crediya.model.reporte;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConstantesTest {

    @Test
    void shouldReturnCorrectIdReportesCrediya() {
        String result = Constantes.ID_REPORTES_CREDIYA;

        assertEquals("id_reportes", result);
    }

    @Test
    void shouldReturnCorrectMensajeReporteNoEncontradoConId() {
        String result = Constantes.MENSAJE_REPORTE_NO_ENCONTRADO_CON_ID;

        assertEquals("Reporte no encontrado con id: ", result);
    }

    @Test
    void shouldReturnCorrectReporteEncontrado() {
        String result = Constantes.REPORTE_ENCONTRADO;

        assertEquals("Reporte encontrado con id", result);
    }

    @Test
    void shouldReturnCorrectReporteNoEncontrado() {
        String result = Constantes.REPORTE_NO_ENCONTRADO;

        assertEquals("Reporte no encontrado con id", result);
    }

    @Test
    void shouldReturnCorrectErrorDeserializando() {
        String result = Constantes.ERROR_DESERIALIZANDO;

        assertEquals("Error deserializando mensaje entrante", result);
    }
}
