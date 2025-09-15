package co.com.crediya.sqs.listener;

import co.com.crediya.model.reporte.Constantes;
import co.com.crediya.model.reporte.Reporte;
import co.com.crediya.model.reporte.excepciones.ExcepcionDominio;
import co.com.crediya.model.reporte.logger.LoggerGateway;
import co.com.crediya.sqs.listener.dto.SolicitudAprobadaDto;
import co.com.crediya.usecase.actualizarreporte.ActualizarReporteUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final ActualizarReporteUseCase actualizarReporteUseCase;
    private final ObjectMapper objectMapper;
    private final LoggerGateway loggerGateway;

    @Override
    public Mono<Void> apply(Message message) {
        SolicitudAprobadaDto solicitudAprobadaDto;
        try {
            solicitudAprobadaDto = objectMapper.readValue(message.body(), SolicitudAprobadaDto.class);
        } catch (JsonProcessingException e) {
            loggerGateway.error("Error deserializando mensaje entrante: {}", message.body());
            throw new ExcepcionDominio(Constantes.ERROR_DESERIALIZANDO + " " + message.body());
        }

        return Mono.just(solicitudAprobadaDto).flatMap(solicitudAprobada -> actualizarReporteUseCase.apply(Reporte.builder()
                .montoPrestamosAprobados(solicitudAprobada.getMonto())
                .id(Constantes.ID_REPORTES_CREDIYA)
                .build())).then();
    }
}
