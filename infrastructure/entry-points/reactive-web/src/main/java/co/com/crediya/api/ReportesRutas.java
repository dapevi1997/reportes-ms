package co.com.crediya.api;

import co.com.crediya.api.config.ReportesRuta;
import co.com.crediya.api.openapi.ReportesOpenApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

@Configuration
@RequiredArgsConstructor
public class ReportesRutas {
    private final ReportesRuta reportesRuta;
    @Bean
    public RouterFunction<ServerResponse> routerFunction(ReportesProcesador reportesProcesador) {
        return route()
                .GET(reportesRuta.getReportes(), reportesProcesador::generarReporte, ReportesOpenApi::generarReportes)
                .build();
    }
}
