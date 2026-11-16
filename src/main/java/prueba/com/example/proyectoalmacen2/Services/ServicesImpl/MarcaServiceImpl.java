package prueba.com.example.proyectoalmacen2.Services.ServicesImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prueba.com.example.proyectoalmacen2.Dto.Request.MarcaRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.MarcaResponse;
import prueba.com.example.proyectoalmacen2.Entity.MarcaEntity;
import prueba.com.example.proyectoalmacen2.Entity.UsuarioEntity;
import prueba.com.example.proyectoalmacen2.Exception.BadRequestException;
import prueba.com.example.proyectoalmacen2.Exception.ResourceNotFoundException;
import prueba.com.example.proyectoalmacen2.Mapper.MarcaMapper;
import prueba.com.example.proyectoalmacen2.Repository.MarcaRepository;
import prueba.com.example.proyectoalmacen2.Repository.UsuarioRepository;
import prueba.com.example.proyectoalmacen2.Services.HistorialService;
import prueba.com.example.proyectoalmacen2.Services.MarcaService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarcaServiceImpl implements MarcaService {

    private final MarcaRepository marcaRepository;
    private final MarcaMapper marcaMapper;
    private final UsuarioRepository usuarioRepository;
    private final HistorialService historialService;

    @Override
    @Transactional
    public MarcaResponse crear(MarcaRequest request, Long usuarioId) {
        if (marcaRepository.existsByNombreIgnoreCase(request.getNombre().trim())) {
            throw new BadRequestException("La marca '" + request.getNombre() + "' ya existe");
        }

        MarcaEntity entity = marcaMapper.toEntity(request);
        entity = marcaRepository.save(entity);

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId));

        historialService.registrarAccion(usuario, "CREACION_MARCA", "ALMACEN",
                "Creó la marca: " + entity.getNombre());

        return marcaMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MarcaResponse> listar() {
        return marcaRepository.findAll()
                .stream()
                .map(marcaMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MarcaResponse obtenerPorId(Long id) {
        MarcaEntity entity = marcaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Marca no encontrada con ID: " + id));
        return marcaMapper.toResponse(entity);
    }
}