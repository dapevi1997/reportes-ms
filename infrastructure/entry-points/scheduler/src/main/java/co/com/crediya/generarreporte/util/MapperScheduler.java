package co.com.crediya.generarreporte.util;

import co.com.crediya.generarreporte.dto.SolicitudDto;
import co.com.crediya.model.solicitud.Solicitud;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class MapperScheduler {

    public List<SolicitudDto> listaSolicitudAListaSolicitudDto(List<Solicitud> list){
        return list.stream().map(solicitud -> SolicitudDto.builder()
                        .estado(solicitud.getEstado())
                        .monto(solicitud.getMonto())
                        .email(solicitud.getEmail())
                        .fechaCreacion(solicitud.getFechaCreacion())
                        .build())
                .toList();
    }
}
