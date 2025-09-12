package co.com.crediya.model.reporte.gateways;

import co.com.crediya.model.reporte.Reporte;
import reactor.core.publisher.Mono;

public interface ReporteRepository {
    Mono<Reporte> getById(String id);
    Mono<Reporte> save(Reporte reporte);
}
