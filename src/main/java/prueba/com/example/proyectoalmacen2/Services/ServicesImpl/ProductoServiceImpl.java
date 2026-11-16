package prueba.com.example.proyectoalmacen2.Services.ServicesImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prueba.com.example.proyectoalmacen2.Dto.Request.ProductoRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.MensajeResponse;
import prueba.com.example.proyectoalmacen2.Dto.Response.ProductoResponse;
import prueba.com.example.proyectoalmacen2.Entity.*;
import prueba.com.example.proyectoalmacen2.Exception.BadRequestException;
import prueba.com.example.proyectoalmacen2.Exception.ResourceNotFoundException;
import prueba.com.example.proyectoalmacen2.Mapper.ProductoMapper;
import prueba.com.example.proyectoalmacen2.Repository.*;
import prueba.com.example.proyectoalmacen2.Services.HistorialService;
import prueba.com.example.proyectoalmacen2.Services.ProductoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;
    private final ModeloRepository modeloRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoMapper productoMapper;
    private final HistorialService historialService;

    @Override
    @Transactional
    public ProductoResponse crear(ProductoRequest request, Long usuarioId) {
        CategoriaEntity categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + request.getCategoriaId()));

        MarcaEntity marca = marcaRepository.findById(request.getMarcaId())
                .orElseThrow(() -> new ResourceNotFoundException("Marca no encontrada con ID: " + request.getMarcaId()));

        ModeloEntity modelo = modeloRepository.findById(request.getModeloId())
                .orElseThrow(() -> new ResourceNotFoundException("Modelo no encontrado con ID: " + request.getModeloId()));

        if (request.getCodigo() == null || request.getCodigo().trim().isEmpty()) {
            request.setCodigo("EQ-" + System.currentTimeMillis() % 1000000);
        } else if (productoRepository.existsByCodigo(request.getCodigo().trim())) {
            throw new BadRequestException("El código de producto '" + request.getCodigo() + "' ya existe");
        }

        ProductoEntity producto = productoMapper.toEntity(request, categoria, marca, modelo);
        producto = productoRepository.save(producto);

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId));

        historialService.registrarAccion(usuario, "REGISTRO_PRODUCTO", "ALMACEN",
                "Registró el equipo: " + producto.getNombre() + " (Stock inicial: " + producto.getStock() + ")");

        return productoMapper.toResponse(producto);
    }

    @Override
    @Transactional
    public ProductoResponse actualizar(Long id, ProductoRequest request, Long usuarioId) {
        ProductoEntity producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        CategoriaEntity categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        MarcaEntity marca = marcaRepository.findById(request.getMarcaId())
                .orElseThrow(() -> new ResourceNotFoundException("Marca no encontrada"));
        ModeloEntity modelo = modeloRepository.findById(request.getModeloId())
                .orElseThrow(() -> new ResourceNotFoundException("Modelo no encontrado"));

        producto.setNombre(request.getNombre().trim());
        producto.setDescripcion(request.getDescripcion() != null ? request.getDescripcion().trim() : null);
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setStockMinimo(request.getStockMinimo() != null ? request.getStockMinimo() : 5);
        if (request.getFechaIngreso() != null) {
            producto.setFechaIngreso(request.getFechaIngreso());
        }
        producto.setCategoria(categoria);
        producto.setMarca(marca);
        producto.setModelo(modelo);

        producto = productoRepository.save(producto);

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId));

        historialService.registrarAccion(usuario, "EDICION_PRODUCTO", "ALMACEN",
                "Actualizó el equipo " + producto.getNombre() + " (Stock: " + producto.getStock() + ")");

        return productoMapper.toResponse(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorId(Long id) {
        ProductoEntity producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        return productoMapper.toResponse(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listarTodos() {
        return productoRepository.findByActivoTrue()
                .stream()
                .map(productoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> buscarConFiltros(String nombre, Long categoriaId, Long marcaId) {
        String nombreFiltro = (nombre != null && !nombre.trim().isEmpty()) ? nombre.trim() : null;
        return productoRepository.buscarConFiltros(nombreFiltro, categoriaId, marcaId)
                .stream()
                .map(productoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listarStockBajo() {
        return productoRepository.findProductosConStockBajo()
                .stream()
                .map(productoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public MensajeResponse cambiarEstado(Long id, boolean activo, Long usuarioId) {
        ProductoEntity producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        producto.setActivo(activo);
        productoRepository.save(producto);

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId));

        String accion = activo ? "ACTIVACION_PRODUCTO" : "DESACTIVACION_PRODUCTO";
        historialService.registrarAccion(usuario, accion, "ALMACEN",
                (activo ? "Activó" : "Desactivó") + " el producto: " + producto.getNombre());

        return MensajeResponse.builder()
                .mensaje("Producto " + (activo ? "activado" : "desactivado") + " con éxito")
                .exito(true)
                .build();
    }
}