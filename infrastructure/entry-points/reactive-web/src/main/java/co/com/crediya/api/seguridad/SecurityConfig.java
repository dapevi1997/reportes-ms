package co.com.crediya.api.seguridad;

import co.com.crediya.api.config.ReportesRuta;
import co.com.crediya.api.dto.RespuestaErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.List;

import static co.com.crediya.api.seguridad.util.Roles.ADMIN;


@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final SecurityContextRepository securityContextRepository;
    private final ReportesRuta rutas;

    private static final ServerResponse.Context DEFAULT_CONTEXT = new ServerResponse.Context() {
        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return HandlerStrategies.withDefaults().messageWriters();
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return HandlerStrategies.withDefaults().viewResolvers();
        }
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .securityContextRepository(securityContextRepository)
                .exceptionHandling(exceptions -> {
                    exceptions.authenticationEntryPoint((exchange, ex) ->
                            ServerResponse.status(HttpStatus.UNAUTHORIZED)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(RespuestaErrorDto.builder()
                                            .httpStatus(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                                            .message(ex.getMessage())
                                            .build())
                                    .flatMap(res -> res.writeTo(exchange, DEFAULT_CONTEXT))
                    );
                    exceptions.accessDeniedHandler((exchange, ex) ->
                            ServerResponse.status(HttpStatus.FORBIDDEN)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .bodyValue(RespuestaErrorDto.builder()
                                            .httpStatus(HttpStatus.FORBIDDEN.getReasonPhrase())
                                            .message(ex.getMessage())
                                            .build())
                                    .flatMap(res -> res.writeTo(exchange, DEFAULT_CONTEXT))
                    );
                })
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/v1/login/**").permitAll()
                        .pathMatchers("/api/v1/reportes/swagger-docs/**", "/api-docs/**", "/webjars/**", "/swagger-ui/**").permitAll()
                        .pathMatchers("/actuator/health").permitAll()
                        .pathMatchers(HttpMethod.GET, rutas.getReportes()).hasAnyRole(ADMIN.name())
                        .anyExchange().authenticated()
                )
                .build();
    }
}
