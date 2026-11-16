package prueba.com.example.proyectoalmacen2.Mapper;

import org.springframework.stereotype.Component;
import prueba.com.example.proyectoalmacen2.Dto.Request.UsuarioCreateRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.LoginResponse;
import prueba.com.example.proyectoalmacen2.Dto.Response.UsuarioResponse;
import prueba.com.example.proyectoalmacen2.Entity.UsuarioEntity;

@Component
public class UsuarioMapper {

    public UsuarioEntity toEntity(UsuarioCreateRequest request) {
        if (request == null) return null;

        UsuarioEntity entity = new UsuarioEntity();
        entity.setNombres(request.getNombres().trim());
        entity.setApellidos(request.getApellidos().trim());
        entity.setUsername(request.getUsername().trim());
        entity.setPassword(request.getPassword()); // En el service se puede encriptar o guardar
        entity.setDocumentoIdentidad(request.getDocumentoIdentidad().trim());
        entity.setFechaNacimiento(request.getFechaNacimiento());
        entity.setRol(request.getRol());
        entity.setActivo(true);
        entity.setRequiereCambioPassword(true);
        return entity;
    }

    public UsuarioResponse toResponse(UsuarioEntity entity) {
        if (entity == null) return null;

        String nombreCompleto = entity.getNombres() + " " + entity.getApellidos();

        return UsuarioResponse.builder()
                .id(entity.getId())
                .nombres(entity.getNombres())
                .apellidos(entity.getApellidos())
                .nombreCompleto(nombreCompleto)
                .username(entity.getUsername())
                .documentoIdentidad(entity.getDocumentoIdentidad())
                .fechaNacimiento(entity.getFechaNacimiento())
                .rol(entity.getRol())
                .activo(entity.getActivo())
                .requiereCambioPassword(entity.getRequiereCambioPassword())
                .fechaCreacion(entity.getFechaCreacion())
                .build();
    }

    public LoginResponse toLoginResponse(UsuarioEntity entity, String mensaje) {
        if (entity == null) return null;

        String nombreCompleto = entity.getNombres() + " " + entity.getApellidos();

        return LoginResponse.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .nombres(entity.getNombres())
                .apellidos(entity.getApellidos())
                .nombreCompleto(nombreCompleto)
                .rol(entity.getRol())
                .requiereCambioPassword(entity.getRequiereCambioPassword())
                .mensaje(mensaje)
                .build();
    }
}