package prueba.com.example.proyectoalmacen2.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import prueba.com.example.proyectoalmacen2.Entity.*;
import prueba.com.example.proyectoalmacen2.Repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;
    private final ModeloRepository modeloRepository;
    private final ProductoRepository productoRepository;
    private final HistorialAccionRepository historialRepository;

    @Override
    @Transactional
    public void run(String... args) {
        inicializarUsuarios();
        inicializarInventario();
    }

    private void inicializarUsuarios() {
        if (usuarioRepository.count() > 0) return;

        // 1. Administrador (no requiere cambio de contraseña inicial)
        UsuarioEntity admin = new UsuarioEntity();
        admin.setNombres("Administrador");
        admin.setApellidos("General");
        admin.setUsername("admin123");
        admin.setPassword("admin123");
        admin.setDocumentoIdentidad("10000001");
        admin.setFechaNacimiento(LocalDate.of(1999, 2, 27));
        admin.setRol(RolUsuarioEntity.ADMINISTRADOR);
        admin.setActivo(true);
        admin.setRequiereCambioPassword(false);
        admin.setFechaCreacion(LocalDateTime.now());
        admin = usuarioRepository.save(admin);

        // 2. Supervisor (requiere cambio obligatorio en primer login)
        UsuarioEntity supervisor = new UsuarioEntity();
        supervisor.setNombres("Carlos");
        supervisor.setApellidos("Mendoza");
        supervisor.setUsername("super123");
        supervisor.setPassword("super123");
        supervisor.setDocumentoIdentidad("20000002");
        supervisor.setFechaNacimiento(LocalDate.of(1999, 2, 27));
        supervisor.setRol(RolUsuarioEntity.SUPERVISOR);
        supervisor.setActivo(true);
        supervisor.setRequiereCambioPassword(true);
        supervisor.setFechaCreacion(LocalDateTime.now());
        usuarioRepository.save(supervisor);

        // 3. Coordinador (requiere cambio obligatorio en primer login)
        UsuarioEntity coordinador = new UsuarioEntity();
        coordinador.setNombres("Ana");
        coordinador.setApellidos("Rojas");
        coordinador.setUsername("coor123");
        coordinador.setPassword("coor123");
        coordinador.setDocumentoIdentidad("30000003");
        coordinador.setFechaNacimiento(LocalDate.of(1999, 2, 27));
        coordinador.setRol(RolUsuarioEntity.COORDINADOR);
        coordinador.setActivo(true);
        coordinador.setRequiereCambioPassword(true);
        coordinador.setFechaCreacion(LocalDateTime.now());
        usuarioRepository.save(coordinador);

        // Registro en historial
        HistorialAccionEntity h = new HistorialAccionEntity();
        h.setUsuario(admin);
        h.setAccion("INICIALIZACION_SISTEMA");
        h.setModulo("SISTEMA");
        h.setDetalle("Se precargaron los usuarios iniciales (Admin, Supervisor, Coordinador)");
        h.setFechaHora(LocalDateTime.now());
        historialRepository.save(h);

        System.out.println(">>> [DataInitializer] Usuarios iniciales creados con éxito.");
    }

    private void inicializarInventario() {
        if (productoRepository.count() > 0) return;

        // Categorías
        CategoriaEntity catMonitor = categoriaRepository.save(new CategoriaEntity(null, "Monitores", "Pantallas y monitores"));
        CategoriaEntity catTeclado = categoriaRepository.save(new CategoriaEntity(null, "Teclados", "Teclados mecánicos y de membrana"));
        CategoriaEntity catMouse = categoriaRepository.save(new CategoriaEntity(null, "Mouse", "Mouses inalámbricos y ópticos"));
        CategoriaEntity catAudio = categoriaRepository.save(new CategoriaEntity(null, "Auriculares", "Headsets y audífonos con micrófono"));
        CategoriaEntity catCpu = categoriaRepository.save(new CategoriaEntity(null, "Computadoras / CPU", "Torres y equipos de escritorio"));

        // Marcas
        MarcaEntity marcaSamsung = marcaRepository.save(new MarcaEntity(null, "Samsung"));
        MarcaEntity marcaLogitech = marcaRepository.save(new MarcaEntity(null, "Logitech"));
        MarcaEntity marcaRedragon = marcaRepository.save(new MarcaEntity(null, "Redragon"));
        MarcaEntity marcaDell = marcaRepository.save(new MarcaEntity(null, "Dell"));

        // Modelos
        ModeloEntity modSamsung = modeloRepository.save(new ModeloEntity(null, "Curvo 24 IPS FHD", "Panel curvo 75Hz", marcaSamsung));
        ModeloEntity modK120 = modeloRepository.save(new ModeloEntity(null, "K120 USB", "Teclado estándar resistente a salpicaduras", marcaLogitech));
        ModeloEntity modM170 = modeloRepository.save(new ModeloEntity(null, "M170 Wireless", "Inalámbrico 2.4GHz", marcaLogitech));
        ModeloEntity modH260 = modeloRepository.save(new ModeloEntity(null, "Hylas H260 RGB", "Sonido estéreo con micrófono flexible", marcaRedragon));
        ModeloEntity modOptiplex = modeloRepository.save(new ModeloEntity(null, "OptiPlex 7090", "Intel Core i7 16GB RAM SSD 512GB", marcaDell));

        // 5 Productos Tecnológicos con Precio incluido
        productoRepository.save(new ProductoEntity(null, "EQ-MON-001", "Monitor Samsung 24\" Curvo",
                "Monitor IPS FHD 75Hz para oficina", new BigDecimal("650.00"), 15, 5, LocalDate.now(), true, catMonitor, marcaSamsung, modSamsung));

        productoRepository.save(new ProductoEntity(null, "EQ-TEC-002", "Teclado Logitech K120",
                "Teclado alámbrico USB en español", new BigDecimal("45.00"), 30, 10, LocalDate.now(), true, catTeclado, marcaLogitech, modK120));

        productoRepository.save(new ProductoEntity(null, "EQ-MOU-003", "Mouse Logitech M170",
                "Mouse inalámbrico con receptor USB", new BigDecimal("55.00"), 25, 8, LocalDate.now(), true, catMouse, marcaLogitech, modM170));

        productoRepository.save(new ProductoEntity(null, "EQ-AUD-004", "Auriculares Redragon Hylas",
                "Headset gamer RGB con micrófono para llamadas", new BigDecimal("120.00"), 10, 4, LocalDate.now(), true, catAudio, marcaRedragon, modH260));

        // Este producto tiene stock 3 y stock mínimo 5, para probar la alerta de stock bajo:
        productoRepository.save(new ProductoEntity(null, "EQ-CPU-005", "CPU Dell OptiPlex 7090",
                "Computadora de escritorio Intel Core i7 16GB", new BigDecimal("3200.00"), 3, 5, LocalDate.now(), true, catCpu, marcaDell, modOptiplex));

        System.out.println(">>> [DataInitializer] 5 Productos tecnológicos cargados con éxito.");
    }
}