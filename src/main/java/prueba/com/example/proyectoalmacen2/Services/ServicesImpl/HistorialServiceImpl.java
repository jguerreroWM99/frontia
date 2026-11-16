package prueba.com.example.proyectoalmacen2.Services.ServicesImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prueba.com.example.proyectoalmacen2.Dto.Response.HistorialAccionResponse;
import prueba.com.example.proyectoalmacen2.Entity.HistorialAccionEntity;
import prueba.com.example.proyectoalmacen2.Entity.RolUsuarioEntity;
import prueba.com.example.proyectoalmacen2.Entity.UsuarioEntity;
import prueba.com.example.proyectoalmacen2.Exception.ResourceNotFoundException;
import prueba.com.example.proyectoalmacen2.Mapper.HistorialAccionMapper;
import prueba.com.example.proyectoalmacen2.Repository.HistorialAccionRepository;
import prueba.com.example.proyectoalmacen2.Repository.UsuarioRepository;
import prueba.com.example.proyectoalmacen2.Services.HistorialService;
import prueba.com.example.proyectoalmacen2.Util.AppConstants;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistorialServiceImpl implements HistorialService {

    private final HistorialAccionRepository historialRepository;
    private final HistorialAccionMapper historialMapper;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public void registrarAccion(UsuarioEntity usuario, String accion, String modulo, String detalle) {
        HistorialAccionEntity historial = new HistorialAccionEntity();
        historial.setUsuario(usuario);
        historial.setAccion(accion);
        historial.setModulo(modulo);
        historial.setDetalle(detalle);
        historial.setFechaHora(LocalDateTime.now());
        historialRepository.save(historial);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistorialAccionResponse> listarPorUsuario(Long usuarioId) {
        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId));

        List<String> accionesPermitidas = obtenerAccionesPermitidasPorRol(usuario.getRol());

        return historialRepository.findByUsuarioIdAndAccionInOrderByFechaHoraDesc(usuarioId, accionesPermitidas)
                .stream()
                .map(historialMapper::toResponse)
                .toList();
    }

    private List<String> obtenerAccionesPermitidasPorRol(RolUsuarioEntity rol) {
        if (rol == RolUsuarioEntity.ADMINISTRADOR || rol == RolUsuarioEntity.SUPERVISOR) {
            return List.of(
                    AppConstants.ACCION_REGISTRO_PRODUCTO,
                    AppConstants.ACCION_CREACION_USUARIO
            );
        }
        return List.of(AppConstants.ACCION_REGISTRO_PRODUCTO);
    }
}