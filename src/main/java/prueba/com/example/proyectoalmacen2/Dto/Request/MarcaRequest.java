package prueba.com.example.proyectoalmacen2.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarcaRequest {

    @NotBlank(message = "El nombre de la marca es obligatorio")
    private String nombre;
}