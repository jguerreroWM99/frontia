package prueba.com.example.proyectoalmacen2.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponse {

    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private Integer stockMinimo;
    private LocalDate fechaIngreso;
    private Boolean activo;

    // Datos desglosados de Categoría
    private Long categoriaId;
    private String categoriaNombre;

    // Datos desglosados de Marca
    private Long marcaId;
    private String marcaNombre;

    // Datos desglosados de Modelo
    private Long modeloId;
    private String modeloNombre;

    // Indicador visual para Angular (stock <= stockMinimo)
    private Boolean alertaStockBajo;
}