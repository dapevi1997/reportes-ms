package co.com.crediya.api.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constantes {

    @UtilityClass
    public static class TipoReporte {
        public final String CANTIDAD = "cantidad";
        public final String MONTO = "monto";
    }

    @UtilityClass
    public static class MensajesError {
        public static final String TIPO_REPORTE_INVALIDO = "Tipo de reporte inválido";
    }
}
