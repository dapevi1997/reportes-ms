package co.com.crediya.api.openapi;

import co.com.crediya.api.dto.RespuestaErrorDto;
import co.com.crediya.api.dto.RespuestaGenerarReporteCantidadDto;
import co.com.crediya.api.dto.RespuestaGenerarReporteMontoDto;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;
import org.springdoc.core.fn.builders.operation.Builder;

@UtilityClass
public class ReportesOpenApi {
    private final String CODIGO_EXITO = String.valueOf(HttpStatus.OK.value());
    private final String CODIGO_CONFLICTO = String.valueOf(HttpStatus.CONFLICT.value());
    private final String CODIGO_MAL_PETICION = String.valueOf(HttpStatus.BAD_REQUEST.value());
    private final String ERROR_INTERNO = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());

    public Builder generarReportes(Builder builder) {
        return builder
                .operationId("generarReportes")
                .tag("Reportes")
                .summary("Reportes CrediYa")
                .parameter(org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder().name("tipo").in(ParameterIn.QUERY))
                //.security(org.springdoc.core.fn.builders.securityrequirement.Builder.securityRequirementBuilder().name("bearerAuth"))

                // 200 Éxito
                .response(responseBuilder()
                        .responseCode(CODIGO_EXITO)
                        .description("Reporte generado correctamente")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(RespuestaGenerarReporteMontoDto.class))))

                // 200 Éxito
                .response(responseBuilder()
                        .responseCode(CODIGO_EXITO)
                        .description("Reporte generado correctamente")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(RespuestaGenerarReporteCantidadDto.class))))

                // 400 Bad Request
                .response(responseBuilder()
                        .responseCode(CODIGO_MAL_PETICION)
                        .description("Error en la solicitud")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(RespuestaErrorDto.class))))

                // 400 Bad Request
                .response(responseBuilder()
                        .responseCode(CODIGO_CONFLICTO)
                        .description("Error en la solicitud")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(RespuestaErrorDto.class))))
                //TODO: respuesta de forbbiden

                // 500 Internal Server Error
                .response(responseBuilder()
                        .responseCode(ERROR_INTERNO)
                        .description("Error interno del servidor")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(RespuestaErrorDto.class))));
    }

}
