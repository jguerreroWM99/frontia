package prueba.com.example.proyectoalmacen2.Services;

import prueba.com.example.proyectoalmacen2.Dto.Request.MarcaRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.MarcaResponse;

import java.util.List;

public interface MarcaService {
    MarcaResponse crear(MarcaRequest request, Long usuarioId);
    List<MarcaResponse> listar();
    MarcaResponse obtenerPorId(Long id);
}