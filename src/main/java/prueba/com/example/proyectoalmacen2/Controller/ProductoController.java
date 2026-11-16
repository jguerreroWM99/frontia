package prueba.com.example.proyectoalmacen2.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prueba.com.example.proyectoalmacen2.Dto.Request.ProductoRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.MensajeResponse;
import prueba.com.example.proyectoalmacen2.Dto.Response.ProductoResponse;
import prueba.com.example.proyectoalmacen2.Services.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoResponse> crear(
            @Valid @RequestBody ProductoRequest request,
            @RequestParam Long usuarioId) {
        return new ResponseEntity<>(productoService.crear(request, usuarioId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listarTodos() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoResponse>> buscarConFiltros(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) Long marcaId) {
        return ResponseEntity.ok(productoService.buscarConFiltros(nombre, categoriaId, marcaId));
    }

    @GetMapping("/stock-bajo")
    public ResponseEntity<List<ProductoResponse>> listarStockBajo() {
        return ResponseEntity.ok(productoService.listarStockBajo());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequest request,
            @RequestParam Long usuarioId) {
        return ResponseEntity.ok(productoService.actualizar(id, request, usuarioId));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<MensajeResponse> cambiarEstado(
            @PathVariable Long id,
            @RequestParam boolean activo,
            @RequestParam Long usuarioId) {
        return ResponseEntity.ok(productoService.cambiarEstado(id, activo, usuarioId));
    }
}