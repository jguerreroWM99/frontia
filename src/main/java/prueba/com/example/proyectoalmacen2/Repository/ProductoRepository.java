package prueba.com.example.proyectoalmacen2.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prueba.com.example.proyectoalmacen2.Entity.ProductoEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {

    Optional<ProductoEntity> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<ProductoEntity> findByActivoTrue();

    // Búsqueda y Filtros Combinados (Nombre, Categoría, Marca)
    @Query("SELECT p FROM ProductoEntity p WHERE " +
            "(:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
            "(:categoriaId IS NULL OR p.categoria.id = :categoriaId) AND " +
            "(:marcaId IS NULL OR p.marca.id = :marcaId) AND " +
            "(p.activo = true)")
    List<ProductoEntity> buscarConFiltros(
            @Param("nombre") String nombre,
            @Param("categoriaId") Long categoriaId,
            @Param("marcaId") Long marcaId
    );

    // Alerta de stock bajo para el almacenero / supervisor
    @Query("SELECT p FROM ProductoEntity p WHERE p.stock <= p.stockMinimo AND p.activo = true")
    List<ProductoEntity> findProductosConStockBajo();
}