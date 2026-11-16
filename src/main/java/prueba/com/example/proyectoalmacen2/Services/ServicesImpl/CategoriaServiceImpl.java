package prueba.com.example.proyectoalmacen2.Services.ServicesImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prueba.com.example.proyectoalmacen2.Dto.Request.CategoriaRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.CategoriaResponse;
import prueba.com.example.proyectoalmacen2.Entity.CategoriaEntity;
import prueba.com.example.proyectoalmacen2.Entity.UsuarioEntity;
import prueba.com.example.proyectoalmacen2.Exception.BadRequestException;
import prueba.com.example.proyectoalmacen2.Exception.ResourceNotFoundException;
import prueba.com.example.proyectoalmacen2.Mapper.CategoriaMapper;
import prueba.com.example.proyectoalmacen2.Repository.CategoriaRepository;
import prueba.com.example.proyectoalmacen2.Repository.UsuarioRepository;
import prueba.com.example.proyectoalmacen2.Services.CategoriaService;
import prueba.com.example.proyectoalmacen2.Services.HistorialService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;
    private final UsuarioRepository usuarioRepository;
    private final HistorialService historialService;

    @Override
    @Transactional
    public CategoriaResponse crear(CategoriaRequest request, Long usuarioId) {
        if (categoriaRepository.existsByNombreIgnoreCase(request.getNombre().trim())) {
            throw new BadRequestException("La categoría '" + request.getNombre() + "' ya existe");
        }

        CategoriaEntity entity = categoriaMapper.toEntity(request);
        entity = categoriaRepository.save(entity);

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId));

        historialService.registrarAccion(usuario, "CREACION_CATEGORIA", "ALMACEN",
                "Creó la categoría: " + entity.getNombre());

        return categoriaMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponse> listar() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponse obtenerPorId(Long id) {
        CategoriaEntity entity = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
        return categoriaMapper.toResponse(entity);
    }
}