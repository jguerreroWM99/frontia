package prueba.com.example.proyectoalmacen2.Mapper;

import org.springframework.stereotype.Component;
import prueba.com.example.proyectoalmacen2.Dto.Response.HistorialAccionResponse;
import prueba.com.example.proyectoalmacen2.Entity.HistorialAccionEntity;

@Component
public class HistorialAccionMapper {

    public HistorialAccionResponse toResponse(HistorialAccionEntity entity) {
        if (entity == null) return null;

        String nombreCompleto = "";
        Long usuarioId = null;
        String username = "";
        var rol = (entity.getUsuario() != null) ? entity.getUsuario().getRol() : null;

        if (entity.getUsuario() != null) {
            usuarioId = entity.getUsuario().getId();
            username = entity.getUsuario().getUsername();
            nombreCompleto = entity.getUsuario().getNombres() + " " + entity.getUsuario().getApellidos();
        }

        return HistorialAccionResponse.builder()
                .id(entity.getId())
                .usuarioId(usuarioId)
                .usuarioUsername(username)
                .usuarioNombreCompleto(nombreCompleto)
                .usuarioRol(rol)
                .accion(entity.getAccion())
                .modulo(entity.getModulo())
                .detalle(entity.getDetalle())
                .fechaHora(entity.getFechaHora())
                .build();
    }
}