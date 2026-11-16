package prueba.com.example.proyectoalmacen2.Services;

import prueba.com.example.proyectoalmacen2.Dto.Request.CategoriaRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.CategoriaResponse;

import java.util.List;

public interface CategoriaService {
    CategoriaResponse crear(CategoriaRequest request, Long usuarioId);
    List<CategoriaResponse> listar();
    CategoriaResponse obtenerPorId(Long id);
}