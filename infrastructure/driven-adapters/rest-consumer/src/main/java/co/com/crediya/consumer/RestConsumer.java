package co.com.crediya.consumer;

import static co.com.crediya.consumer.util.Constantes.*;

import co.com.crediya.jwthelper.JwtService;
import co.com.crediya.model.reporte.logger.LoggerGateway;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.model.solicitud.gateways.SolicitudRepository;
import co.com.crediya.model.usuario.Usuario;
import co.com.crediya.model.usuario.gateways.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestConsumer implements SolicitudRepository, UsuarioRepository {
    private final WebClient client;
    private final JwtService jwtService;
    private final LoggerGateway loggerGateway;

    @Value("${adapter.restconsumer.hostSolicitudes}")
    private String hostSolicitudes;

    @Value("${adapter.restconsumer.hostAutenticacion}")
    private String hostAutenticacion;

    @Value("${adapter.restconsumer.portSolicitudes}")
    private Integer portSolicitudes;

    @Value("${adapter.restconsumer.portAutenticacion}")
    private Integer portAutenticacion;

    @Override
    public Mono<List<Solicitud>> solicitudPorEstado(String estado, Integer offset, Integer limit) {
        // Construir la URI explícitamente
        String uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(hostSolicitudes)
                .port(portSolicitudes)
                .path(OBTENER_SOLICITUDES_POR_ESTADO_PATH)
                .queryParam("estado", estado)
                .queryParam("offset", offset)
                .queryParam("limit", limit)
                .build(true)
                .toUriString();

        // Log de la URI completa
        loggerGateway.info("Realizando petición a URI: {}", uri);

        // Usar esa URI en el WebClient
        return client.get()
                .uri(uri)
                .header("Authorization", "Bearer " + jwtService.generarTokenServicioInterno())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Solicitud>>() {})
                .doOnError(e -> loggerGateway.error("Error al consumir solicitudes por estado: {}", e.getMessage()));
    }

    @Override
    public Mono<List<Usuario>> usuarioPorNombreRol(String nombreRol) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host(hostAutenticacion)
                        .port(portAutenticacion)
                        .path(OBTENER_USUARIOS_POR_NOMBRE_ROL_PATH)
                        .queryParam("rol", nombreRol)
                        .build())
                .header("Authorization", "Bearer " + jwtService.generarTokenServicioInterno())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Usuario>>() {})
                .doOnError(e -> loggerGateway.error("Error al consumir usuario por rol: {}", e.getMessage()));
    }
}
