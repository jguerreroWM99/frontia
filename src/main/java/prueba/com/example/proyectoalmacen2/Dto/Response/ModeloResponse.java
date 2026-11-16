package prueba.com.example.proyectoalmacen2.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModeloResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private Long marcaId;
    private String marcaNombre;
}