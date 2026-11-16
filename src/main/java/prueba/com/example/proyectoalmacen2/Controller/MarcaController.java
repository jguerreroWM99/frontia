package prueba.com.example.proyectoalmacen2.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prueba.com.example.proyectoalmacen2.Dto.Request.MarcaRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.MarcaResponse;
import prueba.com.example.proyectoalmacen2.Services.MarcaService;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService marcaService;

    @PostMapping
    public ResponseEntity<MarcaResponse> crear(
            @Valid @RequestBody MarcaRequest request,
            @RequestParam Long usuarioId) {
        return new ResponseEntity<>(marcaService.crear(request, usuarioId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MarcaResponse>> listar() {
        return ResponseEntity.ok(marcaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(marcaService.obtenerPorId(id));
    }
}