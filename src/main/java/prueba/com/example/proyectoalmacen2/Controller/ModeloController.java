package prueba.com.example.proyectoalmacen2.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prueba.com.example.proyectoalmacen2.Dto.Request.ModeloRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.ModeloResponse;
import prueba.com.example.proyectoalmacen2.Services.ModeloService;

import java.util.List;

@RestController
@RequestMapping("/api/modelos")
@RequiredArgsConstructor
public class ModeloController {

    private final ModeloService modeloService;

    @PostMapping
    public ResponseEntity<ModeloResponse> crear(
            @Valid @RequestBody ModeloRequest request,
            @RequestParam Long usuarioId) {
        return new ResponseEntity<>(modeloService.crear(request, usuarioId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ModeloResponse>> listar() {
        return ResponseEntity.ok(modeloService.listar());
    }

    @GetMapping("/marca/{marcaId}")
    public ResponseEntity<List<ModeloResponse>> listarPorMarca(@PathVariable Long marcaId) {
        return ResponseEntity.ok(modeloService.listarPorMarca(marcaId));
    }
}