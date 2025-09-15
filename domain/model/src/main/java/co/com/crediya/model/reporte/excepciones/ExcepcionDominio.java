package co.com.crediya.model.reporte.excepciones;

public class ExcepcionDominio extends RuntimeException {
    public ExcepcionDominio(String message) {
        super(message);
    }
}
