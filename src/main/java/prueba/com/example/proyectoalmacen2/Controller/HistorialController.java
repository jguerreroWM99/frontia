package prueba.com.example.proyectoalmacen2.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prueba.com.example.proyectoalmacen2.Dto.Response.HistorialAccionResponse;
import prueba.com.example.proyectoalmacen2.Services.HistorialService;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
@RequiredArgsConstructor
public class HistorialController {

    private final HistorialService historialService;

    @GetMapping
    public ResponseEntity<List<HistorialAccionResponse>> listarPorParametro(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(historialService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<HistorialAccionResponse>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(historialService.listarPorUsuario(usuarioId));
    }
}