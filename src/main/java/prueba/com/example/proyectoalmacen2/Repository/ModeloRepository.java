package prueba.com.example.proyectoalmacen2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prueba.com.example.proyectoalmacen2.Entity.ModeloEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModeloRepository extends JpaRepository<ModeloEntity, Long> {

    // Para filtrar modelos por marca seleccionada en Angular
    List<ModeloEntity> findByMarcaId(Long marcaId);

    Optional<ModeloEntity> findByNombreIgnoreCaseAndMarcaId(String nombre, Long marcaId);
}