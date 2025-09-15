package co.com.crediya.usecase.actualizarreporte;

import co.com.crediya.model.reporte.Constantes;
import co.com.crediya.model.reporte.Reporte;
import co.com.crediya.model.reporte.gateways.ReporteRepository;
import co.com.crediya.model.reporte.logger.LoggerGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class ActualizarReporteUseCase {
    private final ReporteRepository reporteRepository;
    private final LoggerGateway loggerGateway;

    public Mono<Reporte> apply(Reporte reporte){
        return reporteRepository.getById(reporte.getId())
                .switchIfEmpty(Mono.defer(() -> {
                    loggerGateway.info("No se encontró reporte con id {}, se usará reporte vacío", reporte.getId());
                    return Mono.just(new Reporte(Constantes.ID_REPORTES_CREDIYA, 0L, BigDecimal.ZERO));
                }))
                .map(reporteExistente -> {
                    loggerGateway.info("Reporte encontrado: {}", reporteExistente);
                    reporteExistente.setCantidadPrestamosAprobados(reporteExistente.getCantidadPrestamosAprobados() + 1);
                    reporteExistente.setMontoPrestamosAprobados(reporte.getMontoPrestamosAprobados().add(reporteExistente.getMontoPrestamosAprobados()));
                    return reporteExistente;
                })
                .flatMap(reporteRepository::save)
                .doOnSuccess(r -> loggerGateway.info("Reporte guardado exitosamente con id {}", r.getId()));
    }
}
