package co.com.crediya.usecase.actualizarreporte;

import co.com.crediya.model.reporte.Constantes;
import co.com.crediya.model.reporte.Reporte;
import co.com.crediya.model.reporte.gateways.ReporteRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class ActualizarReporteUseCase {
    private final ReporteRepository reporteRepository;

    public Mono<Reporte> apply(Reporte reporte){
        return reporteRepository.getById(reporte.getId())
                .switchIfEmpty(Mono.just(new Reporte(Constantes.ID_REPORTES_CREDIYA, 0L, BigDecimal.ZERO)))
                .map(reporteExistente -> {
                    reporteExistente.setCantidadPrestamosAprobados(reporte.getCantidadPrestamosAprobados() + 1);
                    reporteExistente.setMontoPrestamosAprobados(reporte.getMontoPrestamosAprobados().add(reporteExistente.getMontoPrestamosAprobados()));
                    return reporteExistente;
                })
                .flatMap(reporteRepository::save);
    }
}
