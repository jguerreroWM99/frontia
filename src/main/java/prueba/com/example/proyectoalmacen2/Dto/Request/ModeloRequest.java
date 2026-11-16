package prueba.com.example.proyectoalmacen2.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModeloRequest {

    @NotBlank(message = "El nombre del modelo es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "La marca asociada es obligatoria")
    private Long marcaId;
}