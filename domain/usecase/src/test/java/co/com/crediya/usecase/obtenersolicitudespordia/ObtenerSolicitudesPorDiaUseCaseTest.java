package co.com.crediya.usecase.obtenersolicitudespordia;

import co.com.crediya.model.reporte.logger.LoggerGateway;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.model.solicitud.gateways.SolicitudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ObtenerSolicitudesPorDiaUseCaseTest {

    private LoggerGateway loggerGateway;
    private SolicitudRepository solicitudRepository;
    private ObtenerSolicitudesPorDiaUseCase useCase;

    @BeforeEach
    void setUp() {
        loggerGateway = mock(LoggerGateway.class);
        solicitudRepository = mock(SolicitudRepository.class);
        useCase = new ObtenerSolicitudesPorDiaUseCase(loggerGateway, solicitudRepository);
    }

    @Test
    void debeRetornarSolicitudesDeHoy() {
        LocalDate hoy = LocalDate.now();
        Solicitud solicitudHoy = Solicitud.builder()
                .email("test@crediya.com")
                .monto(BigDecimal.valueOf(1000))
                .fechaCreacion(hoy)
                .build();

        when(solicitudRepository.solicitudPorEstado(anyString(), anyInt(), anyInt()))
                .thenReturn(Mono.just(List.of(solicitudHoy)))
                .thenReturn(Mono.just(List.of())); // segunda llamada devuelve vacío → cortar recursión

        StepVerifier.create(useCase.apply(List.of("APROBADO")))
                .expectNextMatches(lista -> lista.size() == 1 && lista.get(0).getEmail().equals("test@crediya.com"))
                .verifyComplete();

        verify(solicitudRepository, atLeastOnce()).solicitudPorEstado("APROBADO", 0, 10);
    }

    @Test
    void debeRetornarListaVaciaCuandoNoHaySolicitudesHoy() {
        LocalDate ayer = LocalDate.now().minusDays(1);
        Solicitud solicitudAyer = Solicitud.builder()
                .email("old@crediya.com")
                .monto(BigDecimal.valueOf(2000))
                .fechaCreacion(ayer)
                .build();

        when(solicitudRepository.solicitudPorEstado(anyString(), anyInt(), anyInt()))
                .thenReturn(Mono.just(List.of(solicitudAyer)))
                .thenReturn(Mono.just(List.of()));

        StepVerifier.create(useCase.apply(List.of("PENDIENTE")))
                .expectNextMatches(List::isEmpty)
                .verifyComplete();
    }

    @Test
    void debeRecorrerMultiplesPaginas() {
        LocalDate hoy = LocalDate.now();

        Solicitud solicitud1 = Solicitud.builder().email("uno@crediya.com").fechaCreacion(hoy).build();
        Solicitud solicitud2 = Solicitud.builder().email("dos@crediya.com").fechaCreacion(hoy).build();

        when(solicitudRepository.solicitudPorEstado("APROBADO", 0, 10))
                .thenReturn(Mono.just(List.of(solicitud1)));
        when(solicitudRepository.solicitudPorEstado("APROBADO", 10, 10))
                .thenReturn(Mono.just(List.of(solicitud2)));
        when(solicitudRepository.solicitudPorEstado("APROBADO", 20, 10))
                .thenReturn(Mono.just(List.of())) // fin
        ;

        StepVerifier.create(useCase.apply(List.of("APROBADO")))
                .expectNextMatches(lista -> lista.size() == 2
                        && lista.get(0).getEmail().equals("uno@crediya.com")
                        && lista.get(1).getEmail().equals("dos@crediya.com"))
                .verifyComplete();

        verify(solicitudRepository, times(3)).solicitudPorEstado(eq("APROBADO"), anyInt(), eq(10));
    }
}
