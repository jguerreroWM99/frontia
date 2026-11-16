package prueba.com.example.proyectoalmacen2.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import prueba.com.example.proyectoalmacen2.Entity.RolUsuarioEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private Long id;
    private String username;
    private String nombres;
    private String apellidos;
    private String nombreCompleto;
    private RolUsuarioEntity rol;
    private Boolean requiereCambioPassword;
    private String mensaje; // Ej: "¡Bienvenido al sistema, Juan Pérez!"
}