package prueba.com.example.proyectoalmacen2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prueba.com.example.proyectoalmacen2.Entity.RolUsuarioEntity;
import prueba.com.example.proyectoalmacen2.Entity.UsuarioEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByDocumentoIdentidad(String documentoIdentidad);

    Optional<UsuarioEntity> findByUsernameAndDocumentoIdentidadAndFechaNacimiento(
            String username,
            String documentoIdentidad,
            LocalDate fechaNacimiento
    );

    List<UsuarioEntity> findByRol(RolUsuarioEntity rol);

    List<UsuarioEntity> findByActivoTrue();

    // Búsqueda filtrada por username y rol
    @Query("SELECT u FROM UsuarioEntity u WHERE " +
            "(:username IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%'))) AND " +
            "(:rol IS NULL OR u.rol = :rol)")
    List<UsuarioEntity> buscarConFiltros(
            @Param("username") String username,
            @Param("rol") RolUsuarioEntity rol
    );
}