package co.com.crediya.usecase.obtenersolicitudespordia;

import co.com.crediya.model.reporte.logger.LoggerGateway;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.model.solicitud.gateways.SolicitudRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
public class ObtenerSolicitudesPorDiaUseCase {
    private final LoggerGateway loggerGateway;
    private final SolicitudRepository solicitudRepository;
    private final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Mono<List<Solicitud>> apply(List<String> estados) {
        return Flux.fromIterable(estados)
                .concatMap(estado -> fetchAllByEstado(estado, 0, 10))
                .collectList();
    }

    private Flux<Solicitud> fetchAllByEstado(String estado, int offset, int limit) {
        LocalDate hoy = LocalDate.now();

        return solicitudRepository.solicitudPorEstado(estado, offset, limit)
                .flatMapMany(list -> {
                    if (list.isEmpty()) {
                        return Flux.empty();
                    }

                    return Flux.fromIterable(list)
                            .filter(solicitud -> {
                                LocalDate fecha = LocalDate.parse(solicitud.getFechaCreacion().format(FORMATO_FECHA));
                                return fecha.equals(hoy);
                            })
                            .concatWith(fetchAllByEstado(estado, offset + limit, limit));
                });
    }
}
