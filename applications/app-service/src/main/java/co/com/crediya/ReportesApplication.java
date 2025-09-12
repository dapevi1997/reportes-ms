package co.com.crediya;

import co.com.crediya.model.reporte.Reporte;
import co.com.crediya.usecase.actualizarreporte.ActualizarReporteUseCase;
import co.com.crediya.usecase.encontrarreporte.EncontrarReporteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.math.BigDecimal;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ReportesApplication implements CommandLineRunner {
    @Autowired
    private EncontrarReporteUseCase encontrarReporteUseCase;
    public static void main(String[] args) {
        SpringApplication.run(ReportesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Reporte reporte = new Reporte();
        reporte.setId("ID");
        reporte.setCantidadPrestamosAprobados(1L);
        reporte.setMontoPrestamosAprobados(BigDecimal.valueOf(200L));

        encontrarReporteUseCase.apply("ID")
                .subscribe(
                        reporte1 -> {
                            System.out.println("************" + reporte1.getCantidadPrestamosAprobados());
                            System.out.println("************" + reporte1.getMontoPrestamosAprobados());
                        }
                );

    }
}
