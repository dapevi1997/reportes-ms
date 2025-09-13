package co.com.crediya.usecase.actualizarreporte;

import co.com.crediya.model.reporte.Constantes;
import co.com.crediya.model.reporte.Reporte;
import co.com.crediya.model.reporte.gateways.ReporteRepository;
import co.com.crediya.model.reporte.logger.LoggerGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ActualizarReporteUseCaseTest {

    private ReporteRepository reporteRepository;
    private LoggerGateway loggerGateway;
    private ActualizarReporteUseCase useCase;

    @BeforeEach
    void setUp() {
        reporteRepository = mock(ReporteRepository.class);
        loggerGateway = mock(LoggerGateway.class);
        useCase = new ActualizarReporteUseCase(reporteRepository, loggerGateway);
    }

    @Test
    void shouldUpdateExistingReporte() {
        // Arrange
        Reporte input = new Reporte("123", 5L, BigDecimal.valueOf(100));
        Reporte existing = new Reporte("123", 2L, BigDecimal.valueOf(50));

        when(reporteRepository.getById("123")).thenReturn(Mono.just(existing));
        when(reporteRepository.save(any(Reporte.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // Act
        Mono<Reporte> result = useCase.apply(input);

        // Assert
        StepVerifier.create(result)
                .assertNext(updated -> {
                    assertEquals("123", updated.getId());
                    assertEquals(3L, updated.getCantidadPrestamosAprobados());
                    assertEquals(BigDecimal.valueOf(150), updated.getMontoPrestamosAprobados());
                })
                .verifyComplete();

        verify(loggerGateway, atLeastOnce()).info(anyString(), any());
        verify(reporteRepository).save(any(Reporte.class));
    }

    @Test
    void shouldCreateEmptyReporteWhenNotFound() {
        // Arrange
        Reporte input = new Reporte("999", 1L, BigDecimal.valueOf(200));

        when(reporteRepository.getById("999")).thenReturn(Mono.empty());
        when(reporteRepository.save(any(Reporte.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // Act
        Mono<Reporte> result = useCase.apply(input);

        // Assert
        StepVerifier.create(result)
                .assertNext(created -> {
                    assertEquals(Constantes.ID_REPORTES_CREDIYA, created.getId());
                    assertEquals(1L, created.getCantidadPrestamosAprobados());
                    assertEquals(BigDecimal.valueOf(200), created.getMontoPrestamosAprobados());
                })
                .verifyComplete();

        verify(loggerGateway, atLeastOnce()).info(anyString(), any());
        verify(reporteRepository).save(any(Reporte.class));
    }

    @Test
    void shouldLogReporteSaved() {
        // Arrange
        Reporte input = new Reporte("456", 3L, BigDecimal.valueOf(300));
        when(reporteRepository.getById("456")).thenReturn(Mono.just(input));
        when(reporteRepository.save(any(Reporte.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // Act
        Mono<Reporte> result = useCase.apply(input);

        // Assert
        StepVerifier.create(result).expectNextCount(1).verifyComplete();

        ArgumentCaptor<String> logCaptor = ArgumentCaptor.forClass(String.class);
        verify(loggerGateway, atLeast(1)).info(logCaptor.capture(), any());

        // Verifica que al menos uno de los logs contenga "guardado exitosamente"
        boolean containsGuardado = logCaptor.getAllValues().stream()
                .anyMatch(msg -> msg.contains("guardado exitosamente"));
        assertEquals(true, containsGuardado);
    }
}
