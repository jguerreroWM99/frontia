package prueba.com.example.proyectoalmacen2.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import prueba.com.example.proyectoalmacen2.Entity.RolUsuarioEntity;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistorialAccionResponse {

    private Long id;
    private Long usuarioId;
    private String usuarioUsername;
    private String usuarioNombreCompleto;
    private RolUsuarioEntity usuarioRol;
    private String accion;
    private String modulo;
    private String detalle;
    private LocalDateTime fechaHora;
}