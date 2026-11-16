package prueba.com.example.proyectoalmacen2.Mapper;

import org.springframework.stereotype.Component;
import prueba.com.example.proyectoalmacen2.Dto.Request.ProductoRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.ProductoResponse;
import prueba.com.example.proyectoalmacen2.Entity.CategoriaEntity;
import prueba.com.example.proyectoalmacen2.Entity.MarcaEntity;
import prueba.com.example.proyectoalmacen2.Entity.ModeloEntity;
import prueba.com.example.proyectoalmacen2.Entity.ProductoEntity;

import java.time.LocalDate;

@Component
public class ProductoMapper {

    public ProductoEntity toEntity(ProductoRequest request,
                                   CategoriaEntity categoria,
                                   MarcaEntity marca,
                                   ModeloEntity modelo) {
        if (request == null) return null;

        ProductoEntity entity = new ProductoEntity();
        entity.setCodigo(request.getCodigo());
        entity.setNombre(request.getNombre().trim());
        entity.setDescripcion(request.getDescripcion() != null ? request.getDescripcion().trim() : null);
        entity.setPrecio(request.getPrecio());
        entity.setStock(request.getStock());
        entity.setStockMinimo(request.getStockMinimo() != null ? request.getStockMinimo() : 5);
        entity.setFechaIngreso(request.getFechaIngreso() != null ? request.getFechaIngreso() : LocalDate.now());
        entity.setActivo(true);
        entity.setCategoria(categoria);
        entity.setMarca(marca);
        entity.setModelo(modelo);
        return entity;
    }

    public ProductoResponse toResponse(ProductoEntity entity) {
        if (entity == null) return null;

        boolean alerta = entity.getStock() != null &&
                entity.getStockMinimo() != null &&
                entity.getStock() <= entity.getStockMinimo();

        return ProductoResponse.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .precio(entity.getPrecio())
                .stock(entity.getStock())
                .stockMinimo(entity.getStockMinimo())
                .fechaIngreso(entity.getFechaIngreso())
                .activo(entity.getActivo())
                .categoriaId(entity.getCategoria() != null ? entity.getCategoria().getId() : null)
                .categoriaNombre(entity.getCategoria() != null ? entity.getCategoria().getNombre() : null)
                .marcaId(entity.getMarca() != null ? entity.getMarca().getId() : null)
                .marcaNombre(entity.getMarca() != null ? entity.getMarca().getNombre() : null)
                .modeloId(entity.getModelo() != null ? entity.getModelo().getId() : null)
                .modeloNombre(entity.getModelo() != null ? entity.getModelo().getNombre() : null)
                .alertaStockBajo(alerta)
                .build();
    }
}