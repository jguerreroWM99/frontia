package prueba.com.example.proyectoalmacen2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prueba.com.example.proyectoalmacen2.Entity.MarcaEntity;

import java.util.Optional;

@Repository
public interface MarcaRepository extends JpaRepository<MarcaEntity, Long> {

    Optional<MarcaEntity> findByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCase(String nombre);
}