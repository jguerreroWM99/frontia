package prueba.com.example.proyectoalmacen2.Services.ServicesImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prueba.com.example.proyectoalmacen2.Dto.Request.CambiarPasswordRequest;
import prueba.com.example.proyectoalmacen2.Dto.Request.LoginRequest;
import prueba.com.example.proyectoalmacen2.Dto.Request.RecuperarPasswordRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.LoginResponse;
import prueba.com.example.proyectoalmacen2.Dto.Response.MensajeResponse;
import prueba.com.example.proyectoalmacen2.Entity.UsuarioEntity;
import prueba.com.example.proyectoalmacen2.Exception.BadRequestException;
import prueba.com.example.proyectoalmacen2.Exception.ResourceNotFoundException;
import prueba.com.example.proyectoalmacen2.Exception.UnauthorizedActionException;
import prueba.com.example.proyectoalmacen2.Mapper.UsuarioMapper;
import prueba.com.example.proyectoalmacen2.Repository.UsuarioRepository;
import prueba.com.example.proyectoalmacen2.Services.AuthService;
import prueba.com.example.proyectoalmacen2.Services.HistorialService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final HistorialService historialService;

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {
        UsuarioEntity usuario = usuarioRepository.findByUsername(request.getUsername().trim())
                .orElseThrow(() -> new BadRequestException("Credenciales incorrectas: usuario no encontrado"));

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            throw new UnauthorizedActionException("La cuenta se encuentra desactivada. Contacte con el administrador.");
        }

        if (!usuario.getPassword().equals(request.getPassword())) {
            throw new BadRequestException("Credenciales incorrectas: contraseña errónea");
        }

        String mensaje = "¡Bienvenido al sistema, " + usuario.getNombres() + " " + usuario.getApellidos() + "!";

        historialService.registrarAccion(usuario, "LOGIN", "AUTENTICACION", "Inició sesión en el sistema");

        return usuarioMapper.toLoginResponse(usuario, mensaje);
    }

    @Override
    @Transactional
    public MensajeResponse cambiarPasswordPrimerLogin(CambiarPasswordRequest request) {
        UsuarioEntity usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + request.getUsuarioId()));

        if (!usuario.getPassword().equals(request.getPasswordActual())) {
            throw new BadRequestException("La contraseña actual no es correcta");
        }

        usuario.setPassword(request.getPasswordNueva());
        usuario.setRequiereCambioPassword(false);
        usuarioRepository.save(usuario);

        historialService.registrarAccion(usuario, "CAMBIO_PASSWORD", "SEGURIDAD", "Actualizó su contraseña en el primer login obligatorio");

        return MensajeResponse.builder()
                .mensaje("Contraseña actualizada con éxito. Ya puedes usar el sistema.")
                .exito(true)
                .build();
    }

    @Override
    @Transactional
    public MensajeResponse recuperarPassword(RecuperarPasswordRequest request) {
        UsuarioEntity usuario = usuarioRepository.findByUsernameAndDocumentoIdentidadAndFechaNacimiento(
                request.getUsername().trim(),
                request.getDocumentoIdentidad().trim(),
                request.getFechaNacimiento()
        ).orElseThrow(() -> new BadRequestException("Los datos no coinciden con nuestros registros. Verifique usuario, documento y fecha de nacimiento."));

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            throw new UnauthorizedActionException("Cuenta inactiva. No se puede recuperar contraseña.");
        }

        usuario.setPassword(request.getNuevaPassword());
        usuario.setRequiereCambioPassword(false);
        usuarioRepository.save(usuario);

        historialService.registrarAccion(usuario, "RECUPERACION_PASSWORD", "SEGURIDAD", "Recuperó su contraseña validando documento y fecha de nacimiento");

        return MensajeResponse.builder()
                .mensaje("Contraseña reestablecida correctamente. Inicie sesión con su nueva clave.")
                .exito(true)
                .build();
    }
}