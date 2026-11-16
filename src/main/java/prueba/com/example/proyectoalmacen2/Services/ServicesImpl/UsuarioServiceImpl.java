package prueba.com.example.proyectoalmacen2.Services.ServicesImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prueba.com.example.proyectoalmacen2.Dto.Request.UsuarioCreateRequest;
import prueba.com.example.proyectoalmacen2.Dto.Request.UsuarioUpdateRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.MensajeResponse;
import prueba.com.example.proyectoalmacen2.Dto.Response.UsuarioResponse;
import prueba.com.example.proyectoalmacen2.Entity.RolUsuarioEntity;
import prueba.com.example.proyectoalmacen2.Entity.UsuarioEntity;
import prueba.com.example.proyectoalmacen2.Exception.BadRequestException;
import prueba.com.example.proyectoalmacen2.Exception.ResourceNotFoundException;
import prueba.com.example.proyectoalmacen2.Exception.UnauthorizedActionException;
import prueba.com.example.proyectoalmacen2.Mapper.UsuarioMapper;
import prueba.com.example.proyectoalmacen2.Repository.UsuarioRepository;
import prueba.com.example.proyectoalmacen2.Services.HistorialService;
import prueba.com.example.proyectoalmacen2.Services.UsuarioService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final HistorialService historialService;

    @Override
    @Transactional
    public UsuarioResponse crearUsuario(UsuarioCreateRequest request, Long solicitanteId) {
        UsuarioEntity solicitante = obtenerUsuarioPorId(solicitanteId);

        if (solicitante.getRol() == RolUsuarioEntity.COORDINADOR) {
            throw new UnauthorizedActionException("El coordinador no tiene permisos para crear cuentas de usuario");
        }

        if (solicitante.getRol() == RolUsuarioEntity.SUPERVISOR) {
            if (request.getRol() != RolUsuarioEntity.COORDINADOR) {
                throw new UnauthorizedActionException("El supervisor únicamente puede crear cuentas de tipo COORDINADOR");
            }
        }

        if (request.getRol() == RolUsuarioEntity.ADMINISTRADOR) {
            throw new UnauthorizedActionException("No está permitido crear más cuentas de Administrador");
        }

        if (usuarioRepository.existsByUsername(request.getUsername().trim())) {
            throw new BadRequestException("El nombre de usuario '" + request.getUsername() + "' ya está registrado");
        }

        if (usuarioRepository.existsByDocumentoIdentidad(request.getDocumentoIdentidad().trim())) {
            throw new BadRequestException("El documento de identidad '" + request.getDocumentoIdentidad() + "' ya existe");
        }

        UsuarioEntity nuevo = usuarioMapper.toEntity(request);
        nuevo = usuarioRepository.save(nuevo);

        historialService.registrarAccion(solicitante, "CREACION_USUARIO", "USUARIOS",
                "Creó al usuario " + nuevo.getUsername() + " con rol " + nuevo.getRol());

        return usuarioMapper.toResponse(nuevo);
    }

    @Override
    @Transactional
    public UsuarioResponse actualizarUsuario(Long id, UsuarioUpdateRequest request, Long solicitanteId) {
        UsuarioEntity solicitante = obtenerUsuarioPorId(solicitanteId);
        UsuarioEntity objetivo = obtenerUsuarioPorId(id);

        if (solicitante.getRol() == RolUsuarioEntity.COORDINADOR && !solicitante.getId().equals(id)) {
            throw new UnauthorizedActionException("El coordinador solo puede actualizar su propio perfil");
        }

        if (solicitante.getRol() == RolUsuarioEntity.SUPERVISOR) {
            if (objetivo.getRol() == RolUsuarioEntity.ADMINISTRADOR ||
                    (objetivo.getRol() == RolUsuarioEntity.SUPERVISOR && !solicitante.getId().equals(id))) {
                throw new UnauthorizedActionException("El supervisor no puede editar perfiles de administradores ni de otros supervisores");
            }
        }

        objetivo.setNombres(request.getNombres().trim());
        objetivo.setApellidos(request.getApellidos().trim());
        objetivo.setDocumentoIdentidad(request.getDocumentoIdentidad().trim());
        objetivo.setFechaNacimiento(request.getFechaNacimiento());

        UsuarioEntity guardado = usuarioRepository.save(objetivo);

        historialService.registrarAccion(solicitante, "EDICION_USUARIO", "USUARIOS",
                "Actualizó los datos del usuario " + guardado.getUsername());

        return usuarioMapper.toResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse obtenerPorId(Long id) {
        return usuarioMapper.toResponse(obtenerUsuarioPorId(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarTodos(Long solicitanteId, String username, RolUsuarioEntity rol) {
        UsuarioEntity solicitante = obtenerUsuarioPorId(solicitanteId);
        String userFiltro = (username != null && !username.trim().isEmpty()) ? username.trim() : null;

        List<UsuarioEntity> resultados = usuarioRepository.buscarConFiltros(userFiltro, rol);

        // Si no es ADMINISTRADOR, ocultamos cualquier cuenta con rol ADMINISTRADOR
        if (solicitante.getRol() != RolUsuarioEntity.ADMINISTRADOR) {
            resultados = resultados.stream()
                    .filter(u -> u.getRol() != RolUsuarioEntity.ADMINISTRADOR)
                    .toList();
        }

        return resultados.stream()
                .map(usuarioMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public MensajeResponse cambiarEstado(Long id, boolean activo, Long solicitanteId) {
        UsuarioEntity solicitante = obtenerUsuarioPorId(solicitanteId);
        UsuarioEntity objetivo = obtenerUsuarioPorId(id);

        if (solicitante.getRol() == RolUsuarioEntity.COORDINADOR) {
            throw new UnauthorizedActionException("El coordinador no tiene permisos para activar o desactivar cuentas");
        }

        if (objetivo.getRol() == RolUsuarioEntity.ADMINISTRADOR) {
            throw new UnauthorizedActionException("No se puede desactivar la cuenta del Administrador");
        }

        if (solicitante.getRol() == RolUsuarioEntity.SUPERVISOR) {
            if (objetivo.getRol() != RolUsuarioEntity.COORDINADOR) {
                throw new UnauthorizedActionException("El supervisor solo puede activar o desactivar a usuarios Coordinadores");
            }
        }

        objetivo.setActivo(activo);
        usuarioRepository.save(objetivo);

        String accion = activo ? "ACTIVACION_USUARIO" : "DESACTIVACION_USUARIO";
        String detalle = (activo ? "Activó" : "Desactivó") + " la cuenta de " + objetivo.getUsername();
        historialService.registrarAccion(solicitante, accion, "USUARIOS", detalle);

        return MensajeResponse.builder()
                .mensaje("Usuario " + (activo ? "activado" : "desactivado") + " correctamente")
                .exito(true)
                .build();
    }

    @Override
    @Transactional
    public MensajeResponse eliminarUsuario(Long id, Long solicitanteId) {
        UsuarioEntity solicitante = obtenerUsuarioPorId(solicitanteId);
        UsuarioEntity objetivo = obtenerUsuarioPorId(id);

        if (solicitante.getRol() == RolUsuarioEntity.COORDINADOR) {
            throw new UnauthorizedActionException("El coordinador no tiene permisos para eliminar cuentas de usuario");
        }

        if (objetivo.getRol() == RolUsuarioEntity.ADMINISTRADOR) {
            throw new UnauthorizedActionException("No se puede eliminar la cuenta del Administrador");
        }

        if (solicitante.getRol() == RolUsuarioEntity.SUPERVISOR) {
            if (objetivo.getRol() != RolUsuarioEntity.COORDINADOR) {
                throw new UnauthorizedActionException("El supervisor únicamente puede eliminar cuentas de tipo COORDINADOR");
            }
        }

        usuarioRepository.delete(objetivo);

        historialService.registrarAccion(solicitante, "ELIMINACION_USUARIO", "USUARIOS",
                "Eliminó la cuenta de " + objetivo.getUsername() + " (" + objetivo.getRol() + ")");

        return MensajeResponse.builder()
                .mensaje("Usuario eliminado correctamente")
                .exito(true)
                .build();
    }

    private UsuarioEntity obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }
}