package co.com.crediya.generarreporte.services;

import co.com.crediya.generarreporte.dto.SolicitudDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GenerarHtmlService {
    private final TemplateEngine templateEngine;

    public String generarHtmlReporteDiario(List<SolicitudDto> solicitudDtoList, String montoTotalAprobados, String cantidadAprobados){
        List<Map<String,Object>> viewList = solicitudDtoList.stream()
                .map(s -> {
                    Map<String,Object> m = new HashMap<>();
                    m.put("email", s.getEmail());
                    m.put("monto", s.getMonto());
                    m.put("estado", s.getEstado());
                    m.put("fechaCreacion", s.getFechaCreacion());
                    return m;
                })
                .toList();

        Context context = new Context();
        context.setVariable("solicitudes", viewList);
        context.setVariable("montoTotalAprobado", montoTotalAprobados);
        context.setVariable("numeroTotalAprobados", cantidadAprobados);

        return templateEngine.process("solicitudes-reporte", context);
    }
}
