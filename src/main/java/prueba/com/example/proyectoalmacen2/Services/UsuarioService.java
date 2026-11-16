package prueba.com.example.proyectoalmacen2.Services;

import prueba.com.example.proyectoalmacen2.Dto.Request.UsuarioCreateRequest;
import prueba.com.example.proyectoalmacen2.Dto.Request.UsuarioUpdateRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.MensajeResponse;
import prueba.com.example.proyectoalmacen2.Dto.Response.UsuarioResponse;
import prueba.com.example.proyectoalmacen2.Entity.RolUsuarioEntity;

import java.util.List;

public interface UsuarioService {
    UsuarioResponse crearUsuario(UsuarioCreateRequest request, Long solicitanteId);
    UsuarioResponse actualizarUsuario(Long id, UsuarioUpdateRequest request, Long solicitanteId);
    UsuarioResponse obtenerPorId(Long id);
    List<UsuarioResponse> listarTodos(Long solicitanteId, String username, RolUsuarioEntity rol);
    MensajeResponse cambiarEstado(Long id, boolean activo, Long solicitanteId);
    MensajeResponse eliminarUsuario(Long id, Long solicitanteId);
}