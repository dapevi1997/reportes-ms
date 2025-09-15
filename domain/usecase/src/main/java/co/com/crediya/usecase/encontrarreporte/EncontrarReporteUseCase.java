package co.com.crediya.usecase.encontrarreporte;

import co.com.crediya.model.reporte.Constantes;
import co.com.crediya.model.reporte.Reporte;
import co.com.crediya.model.reporte.excepciones.ExcepcionDominio;
import co.com.crediya.model.reporte.gateways.ReporteRepository;
import co.com.crediya.model.reporte.logger.LoggerGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class EncontrarReporteUseCase {
    private final ReporteRepository reporteRepository;
    private final LoggerGateway loggerGateway;

    public Mono<Reporte> apply(String id) {
        return reporteRepository.getById(id)
                .doOnNext(r -> loggerGateway.info(Constantes.REPORTE_ENCONTRADO + " {}", id))
                .switchIfEmpty(Mono.defer(() -> {
                    loggerGateway.error(Constantes.REPORTE_NO_ENCONTRADO + " {}", id);
                    return Mono.error(new ExcepcionDominio(Constantes.MENSAJE_REPORTE_NO_ENCONTRADO_CON_ID + id));
                }));
    }
}
