package prueba.com.example.proyectoalmacen2.Mapper;

import org.springframework.stereotype.Component;
import prueba.com.example.proyectoalmacen2.Dto.Request.MarcaRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.MarcaResponse;
import prueba.com.example.proyectoalmacen2.Entity.MarcaEntity;

@Component
public class MarcaMapper {

    public MarcaEntity toEntity(MarcaRequest request) {
        if (request == null) return null;

        MarcaEntity entity = new MarcaEntity();
        entity.setNombre(request.getNombre().trim());
        return entity;
    }

    public MarcaResponse toResponse(MarcaEntity entity) {
        if (entity == null) return null;

        return MarcaResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .build();
    }
}