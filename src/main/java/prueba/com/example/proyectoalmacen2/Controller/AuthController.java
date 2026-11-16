package prueba.com.example.proyectoalmacen2.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prueba.com.example.proyectoalmacen2.Dto.Request.CambiarPasswordRequest;
import prueba.com.example.proyectoalmacen2.Dto.Request.LoginRequest;
import prueba.com.example.proyectoalmacen2.Dto.Request.RecuperarPasswordRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.LoginResponse;
import prueba.com.example.proyectoalmacen2.Dto.Response.MensajeResponse;
import prueba.com.example.proyectoalmacen2.Services.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/cambiar-password")
    public ResponseEntity<MensajeResponse> cambiarPassword(@Valid @RequestBody CambiarPasswordRequest request) {
        return ResponseEntity.ok(authService.cambiarPasswordPrimerLogin(request));
    }

    @PostMapping("/recuperar-password")
    public ResponseEntity<MensajeResponse> recuperarPassword(@Valid @RequestBody RecuperarPasswordRequest request) {
        return ResponseEntity.ok(authService.recuperarPassword(request));
    }
}