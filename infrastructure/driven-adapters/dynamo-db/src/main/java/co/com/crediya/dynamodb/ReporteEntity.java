package co.com.crediya.dynamodb;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.math.BigDecimal;

@DynamoDbBean
public class ReporteEntity {

    private String id;
    private Long cantidadPrestamosAprobados;
    private BigDecimal montoPrestamosAprobados;

    public ReporteEntity() {
    }

    public ReporteEntity(String id, Long cantidadPrestamosAprobados, BigDecimal montoPrestamosAprobados) {
        this.id = id;
        this.cantidadPrestamosAprobados = cantidadPrestamosAprobados;
        this.montoPrestamosAprobados = montoPrestamosAprobados;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbAttribute("cantidad")
    public Long getCantidadPrestamosAprobados() {
        return cantidadPrestamosAprobados;
    }

    public void setCantidadPrestamosAprobados(Long cantidadPrestamosAprobados) {
        this.cantidadPrestamosAprobados = cantidadPrestamosAprobados;
    }

    @DynamoDbAttribute("monto")
    public BigDecimal getMontoPrestamosAprobados() {
        return montoPrestamosAprobados;
    }

    public void setMontoPrestamosAprobados(BigDecimal montoPrestamosAprobados) {
        this.montoPrestamosAprobados = montoPrestamosAprobados;
    }
}
