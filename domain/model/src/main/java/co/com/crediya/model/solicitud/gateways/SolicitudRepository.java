package co.com.crediya.model.solicitud.gateways;

import co.com.crediya.model.solicitud.Solicitud;
import reactor.core.publisher.Mono;

import java.util.List;

public interface SolicitudRepository {
    Mono<List<Solicitud>> solicitudPorEstado(String estado, Integer offset, Integer limit);
}
