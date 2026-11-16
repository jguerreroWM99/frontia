package prueba.com.example.proyectoalmacen2.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prueba.com.example.proyectoalmacen2.Dto.Request.UsuarioCreateRequest;
import prueba.com.example.proyectoalmacen2.Dto.Request.UsuarioUpdateRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.MensajeResponse;
import prueba.com.example.proyectoalmacen2.Dto.Response.UsuarioResponse;
import prueba.com.example.proyectoalmacen2.Entity.RolUsuarioEntity;
import prueba.com.example.proyectoalmacen2.Services.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponse> crear(
            @Valid @RequestBody UsuarioCreateRequest request,
            @RequestParam Long solicitanteId) {
        return new ResponseEntity<>(usuarioService.crearUsuario(request, solicitanteId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar(
            @RequestParam Long solicitanteId,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) RolUsuarioEntity rol) {
        return ResponseEntity.ok(usuarioService.listarTodos(solicitanteId, username, rol));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioUpdateRequest request,
            @RequestParam Long solicitanteId) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, request, solicitanteId));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<MensajeResponse> cambiarEstado(
            @PathVariable Long id,
            @RequestParam boolean activo,
            @RequestParam Long solicitanteId) {
        return ResponseEntity.ok(usuarioService.cambiarEstado(id, activo, solicitanteId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeResponse> eliminar(
            @PathVariable Long id,
            @RequestParam Long solicitanteId) {
        return ResponseEntity.ok(usuarioService.eliminarUsuario(id, solicitanteId));
    }
}