package prueba.com.example.proyectoalmacen2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prueba.com.example.proyectoalmacen2.Entity.HistorialAccionEntity;

import java.util.List;

@Repository
public interface HistorialAccionRepository extends JpaRepository<HistorialAccionEntity, Long> {

    // Filtra el historial propio del usuario únicamente por las acciones permitidas para su rol
    List<HistorialAccionEntity> findByUsuarioIdAndAccionInOrderByFechaHoraDesc(Long usuarioId, List<String> acciones);
}