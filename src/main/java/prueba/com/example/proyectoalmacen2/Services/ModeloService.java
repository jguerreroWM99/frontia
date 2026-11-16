package prueba.com.example.proyectoalmacen2.Services;

import prueba.com.example.proyectoalmacen2.Dto.Request.ModeloRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.ModeloResponse;

import java.util.List;

public interface ModeloService {
    ModeloResponse crear(ModeloRequest request, Long usuarioId);
    List<ModeloResponse> listarPorMarca(Long marcaId);
    List<ModeloResponse> listar();
}