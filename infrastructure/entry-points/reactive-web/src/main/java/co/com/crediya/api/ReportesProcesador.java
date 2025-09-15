package co.com.crediya.api;

import co.com.crediya.api.dto.RespuestaGenerarReporteCantidadDto;
import co.com.crediya.api.dto.RespuestaGenerarReporteMontoDto;
import co.com.crediya.model.reporte.Constantes;
import co.com.crediya.model.reporte.excepciones.PeticionMalFormada;
import co.com.crediya.model.reporte.logger.LoggerGateway;
import co.com.crediya.usecase.encontrarreporte.EncontrarReporteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import static co.com.crediya.api.util.Constantes.TipoReporte;
import static co.com.crediya.api.util.Constantes.MensajesError;
import static co.com.crediya.api.util.Constantes.ParametrosQuery;
import static co.com.crediya.api.util.Constantes.MensajesLogger;

@Component
@RequiredArgsConstructor
public class ReportesProcesador {
    private final EncontrarReporteUseCase encontrarReporteUseCase;
    private final LoggerGateway loggerGateway;

    public Mono<ServerResponse> generarReporte(ServerRequest serverRequest) {
        return Mono.justOrEmpty(serverRequest.queryParam(ParametrosQuery.TIPO_REPORTE))
                .filter(tipo -> {
                    boolean valido = tipo.equals(TipoReporte.CANTIDAD) || tipo.equals(TipoReporte.MONTO);
                    if (!valido) {
                        loggerGateway.error(MensajesLogger.TIPO_REPORTE_NO_PERMITIDO + ": {}", tipo);
                    }
                    return valido;
                })
                .switchIfEmpty(Mono.error(new PeticionMalFormada(MensajesError.TIPO_REPORTE_INVALIDO)))
                .flatMap(tipo -> encontrarReporteUseCase.apply(Constantes.ID_REPORTES_CREDIYA)
                        .flatMap(reporte -> {
                            if (tipo.equals(TipoReporte.CANTIDAD)) {
                                return ServerResponse.ok().bodyValue(RespuestaGenerarReporteCantidadDto.builder()
                                        .cantidad(reporte.getCantidadPrestamosAprobados())
                                        .build());
                            } else {
                                return ServerResponse.ok().bodyValue(RespuestaGenerarReporteMontoDto.builder()
                                        .monto(reporte.getMontoPrestamosAprobados())
                                        .build());
                            }
                        })
                );
    }
}
