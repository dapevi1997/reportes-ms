package co.com.crediya.usecase.encontrarreporte;

import co.com.crediya.model.reporte.Constantes;
import co.com.crediya.model.reporte.Reporte;
import co.com.crediya.model.reporte.excepciones.ExcepcionDominio;
import co.com.crediya.model.reporte.gateways.ReporteRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class EncontrarReporteUseCase {
    private final ReporteRepository reporteRepository;

    public Mono<Reporte> apply(String id){
        return reporteRepository.getById(id)
                .switchIfEmpty(Mono.error(new ExcepcionDominio(Constantes.MENSAJE_REPORTE_NO_ENCONTRADO_CON_ID + id)));
    }
}
