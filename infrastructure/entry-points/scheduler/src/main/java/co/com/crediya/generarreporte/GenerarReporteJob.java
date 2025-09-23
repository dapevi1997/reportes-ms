package co.com.crediya.generarreporte;

import co.com.crediya.generarreporte.services.EnviarEmailService;
import co.com.crediya.generarreporte.services.GenerarHtmlService;
import co.com.crediya.generarreporte.util.Estados;
import co.com.crediya.generarreporte.util.MapperScheduler;
import co.com.crediya.model.reporte.Constantes;
import co.com.crediya.model.reporte.logger.LoggerGateway;
import co.com.crediya.usecase.encontrarreporte.EncontrarReporteUseCase;
import co.com.crediya.usecase.obtenersolicitudespordia.ObtenerSolicitudesPorDiaUseCase;
import co.com.crediya.usecase.obtenerusuariosadministradores.ObtenerUsuariosAdministradoresUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import java.util.*;

@Component
@RequiredArgsConstructor
public class GenerarReporteJob {
    private final GenerarHtmlService generarHtmlService;
    private final EnviarEmailService enviarEmailService;
    private final ObtenerSolicitudesPorDiaUseCase obtenerSolicitudesPorDiaUseCase;
    private final EncontrarReporteUseCase encontrarReporteUseCase;
    private final ObtenerUsuariosAdministradoresUseCase obtenerUsuariosAdministradoresUseCase;
    private final LoggerGateway loggerGateway;

    @Scheduled(cron = "0 0 20 * * *")
    public void generarReporteDiario(){

        obtenerSolicitudesPorDiaUseCase.apply(Arrays.stream(Estados.values())
                .map(Enum::name)
                .toList())
                .map(MapperScheduler::listaSolicitudAListaSolicitudDto)
                .flatMap(list -> encontrarReporteUseCase.apply(Constantes.ID_REPORTES_CREDIYA)
                        .map(reporte -> generarHtmlService.generarHtmlReporteDiario(list, reporte.getMontoPrestamosAprobados().toString(),
                                reporte.getCantidadPrestamosAprobados().toString())))
                .flatMapMany(html ->
                        obtenerUsuariosAdministradoresUseCase.apply()
                                .flatMapMany(Flux::fromIterable)
                                .flatMap(usuario ->
                                        enviarEmailService.enviar(html, Constantes.CORREO_EMISOR, usuario.getEmail())
                                                .doOnSuccess(sendEmailResponse -> loggerGateway.info(Constantes.REPORTE_ENVIADO, usuario.getEmail()))
                                )
                )
                .subscribe(
                        response -> loggerGateway.info(Constantes.CORREO_ENVIADO, response.messageId()),
                        error -> loggerGateway.error(Constantes.ERROR_ENVIANDO_REPORTE, error.getMessage()),
                        () -> loggerGateway.info(Constantes.REPORTE_DIARIO_COMPLETADO)
                );

    }
}
