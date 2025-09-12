package co.com.crediya.model.reporte.excepciones;

public class PeticionMalFormada extends RuntimeException {
    public PeticionMalFormada(String message) {
        super(message);
    }
}
