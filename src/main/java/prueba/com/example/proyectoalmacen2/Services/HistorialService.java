package prueba.com.example.proyectoalmacen2.Services;

import prueba.com.example.proyectoalmacen2.Dto.Response.HistorialAccionResponse;
import prueba.com.example.proyectoalmacen2.Entity.UsuarioEntity;

import java.util.List;

public interface HistorialService {
    void registrarAccion(UsuarioEntity usuario, String accion, String modulo, String detalle);
    List<HistorialAccionResponse> listarPorUsuario(Long usuarioId);
}