package prueba.com.example.proyectoalmacen2.Mapper;

import org.springframework.stereotype.Component;
import prueba.com.example.proyectoalmacen2.Dto.Request.ModeloRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.ModeloResponse;
import prueba.com.example.proyectoalmacen2.Entity.MarcaEntity;
import prueba.com.example.proyectoalmacen2.Entity.ModeloEntity;

@Component
public class ModeloMapper {

    public ModeloEntity toEntity(ModeloRequest request, MarcaEntity marca) {
        if (request == null) return null;

        ModeloEntity entity = new ModeloEntity();
        entity.setNombre(request.getNombre().trim());
        entity.setDescripcion(request.getDescripcion() != null ? request.getDescripcion().trim() : null);
        entity.setMarca(marca);
        return entity;
    }

    public ModeloResponse toResponse(ModeloEntity entity) {
        if (entity == null) return null;

        return ModeloResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .marcaId(entity.getMarca() != null ? entity.getMarca().getId() : null)
                .marcaNombre(entity.getMarca() != null ? entity.getMarca().getNombre() : null)
                .build();
    }
}