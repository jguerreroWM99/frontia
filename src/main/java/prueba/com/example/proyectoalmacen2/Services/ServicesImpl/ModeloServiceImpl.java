package prueba.com.example.proyectoalmacen2.Services.ServicesImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prueba.com.example.proyectoalmacen2.Dto.Request.ModeloRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.ModeloResponse;
import prueba.com.example.proyectoalmacen2.Entity.MarcaEntity;
import prueba.com.example.proyectoalmacen2.Entity.ModeloEntity;
import prueba.com.example.proyectoalmacen2.Entity.UsuarioEntity;
import prueba.com.example.proyectoalmacen2.Exception.BadRequestException;
import prueba.com.example.proyectoalmacen2.Exception.ResourceNotFoundException;
import prueba.com.example.proyectoalmacen2.Mapper.ModeloMapper;
import prueba.com.example.proyectoalmacen2.Repository.MarcaRepository;
import prueba.com.example.proyectoalmacen2.Repository.ModeloRepository;
import prueba.com.example.proyectoalmacen2.Repository.UsuarioRepository;
import prueba.com.example.proyectoalmacen2.Services.HistorialService;
import prueba.com.example.proyectoalmacen2.Services.ModeloService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModeloServiceImpl implements ModeloService {

    private final ModeloRepository modeloRepository;
    private final MarcaRepository marcaRepository;
    private final ModeloMapper modeloMapper;
    private final UsuarioRepository usuarioRepository;
    private final HistorialService historialService;

    @Override
    @Transactional
    public ModeloResponse crear(ModeloRequest request, Long usuarioId) {
        MarcaEntity marca = marcaRepository.findById(request.getMarcaId())
                .orElseThrow(() -> new ResourceNotFoundException("Marca no encontrada con ID: " + request.getMarcaId()));

        if (modeloRepository.findByNombreIgnoreCaseAndMarcaId(request.getNombre().trim(), request.getMarcaId()).isPresent()) {
            throw new BadRequestException("El modelo '" + request.getNombre() + "' ya existe para la marca " + marca.getNombre());
        }

        ModeloEntity entity = modeloMapper.toEntity(request, marca);
        entity = modeloRepository.save(entity);

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId));

        historialService.registrarAccion(usuario, "CREACION_MODELO", "ALMACEN",
                "Creó el modelo: " + entity.getNombre() + " para la marca " + marca.getNombre());

        return modeloMapper.toResponse(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModeloResponse> listarPorMarca(Long marcaId) {
        return modeloRepository.findByMarcaId(marcaId)
                .stream()
                .map(modeloMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModeloResponse> listar() {
        return modeloRepository.findAll()
                .stream()
                .map(modeloMapper::toResponse)
                .toList();
    }
}