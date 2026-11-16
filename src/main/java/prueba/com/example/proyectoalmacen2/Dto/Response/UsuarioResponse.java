package prueba.com.example.proyectoalmacen2.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import prueba.com.example.proyectoalmacen2.Entity.RolUsuarioEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {

    private Long id;
    private String nombres;
    private String apellidos;
    private String nombreCompleto;
    private String username;
    private String documentoIdentidad;
    private LocalDate fechaNacimiento;
    private RolUsuarioEntity rol;
    private Boolean activo;
    private Boolean requiereCambioPassword;
    private LocalDateTime fechaCreacion;
}