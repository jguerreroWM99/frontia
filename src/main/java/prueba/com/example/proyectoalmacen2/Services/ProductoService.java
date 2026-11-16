package prueba.com.example.proyectoalmacen2.Services;

import prueba.com.example.proyectoalmacen2.Dto.Request.ProductoRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.MensajeResponse;
import prueba.com.example.proyectoalmacen2.Dto.Response.ProductoResponse;

import java.util.List;

public interface ProductoService {
    ProductoResponse crear(ProductoRequest request, Long usuarioId);
    ProductoResponse actualizar(Long id, ProductoRequest request, Long usuarioId);
    ProductoResponse obtenerPorId(Long id);
    List<ProductoResponse> listarTodos();
    List<ProductoResponse> buscarConFiltros(String nombre, Long categoriaId, Long marcaId);
    List<ProductoResponse> listarStockBajo();
    MensajeResponse cambiarEstado(Long id, boolean activo, Long usuarioId);
}