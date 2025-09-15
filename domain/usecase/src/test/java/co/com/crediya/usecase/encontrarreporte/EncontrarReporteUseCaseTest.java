package co.com.crediya.usecase.encontrarreporte;

import co.com.crediya.model.reporte.Constantes;
import co.com.crediya.model.reporte.Reporte;
import co.com.crediya.model.reporte.excepciones.ExcepcionDominio;
import co.com.crediya.model.reporte.gateways.ReporteRepository;
import co.com.crediya.model.reporte.logger.LoggerGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EncontrarReporteUseCaseTest {

    private ReporteRepository reporteRepository;
    private LoggerGateway loggerGateway;
    private EncontrarReporteUseCase useCase;

    @BeforeEach
    void setUp() {
        reporteRepository = mock(ReporteRepository.class);
        loggerGateway = mock(LoggerGateway.class);
        useCase = new EncontrarReporteUseCase(reporteRepository, loggerGateway);
    }

    @Test
    void shouldReturnReporteWhenExists() {
        // Arrange
        String id = "123";
        Reporte reporte = new Reporte(id, 5L, null);

        when(reporteRepository.getById(id)).thenReturn(Mono.just(reporte));

        // Act
        Mono<Reporte> result = useCase.apply(id);

        // Assert
        StepVerifier.create(result)
                .expectNext(reporte)
                .verifyComplete();

        verify(loggerGateway).info(Constantes.REPORTE_ENCONTRADO + " {}", id);
        verify(loggerGateway, never()).error(any(), any());
    }

    @Test
    void shouldThrowExcepcionDominioWhenNotFound() {
        // Arrange
        String id = "999";
        when(reporteRepository.getById(id)).thenReturn(Mono.empty());

        // Act
        Mono<Reporte> result = useCase.apply(id);

        // Assert
        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    assertEquals(ExcepcionDominio.class, error.getClass());
                    assertEquals(Constantes.MENSAJE_REPORTE_NO_ENCONTRADO_CON_ID + id, error.getMessage());
                })
                .verify();

        ArgumentCaptor<String> logCaptor = ArgumentCaptor.forClass(String.class);
        verify(loggerGateway).error(logCaptor.capture(), eq(id));

        assertEquals(Constantes.REPORTE_NO_ENCONTRADO + " {}", logCaptor.getValue());
    }
}
