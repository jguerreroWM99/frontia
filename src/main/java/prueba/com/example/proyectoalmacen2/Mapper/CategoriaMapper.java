package prueba.com.example.proyectoalmacen2.Mapper;

import org.springframework.stereotype.Component;
import prueba.com.example.proyectoalmacen2.Dto.Request.CategoriaRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.CategoriaResponse;
import prueba.com.example.proyectoalmacen2.Entity.CategoriaEntity;

@Component
public class CategoriaMapper {

    public CategoriaEntity toEntity(CategoriaRequest request) {
        if (request == null) return null;

        CategoriaEntity entity = new CategoriaEntity();
        entity.setNombre(request.getNombre().trim());
        entity.setDescripcion(request.getDescripcion() != null ? request.getDescripcion().trim() : null);
        return entity;
    }

    public CategoriaResponse toResponse(CategoriaEntity entity) {
        if (entity == null) return null;

        return CategoriaResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .build();
    }
}