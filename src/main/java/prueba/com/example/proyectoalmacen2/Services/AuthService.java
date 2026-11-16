package prueba.com.example.proyectoalmacen2.Services;

import prueba.com.example.proyectoalmacen2.Dto.Request.CambiarPasswordRequest;
import prueba.com.example.proyectoalmacen2.Dto.Request.LoginRequest;
import prueba.com.example.proyectoalmacen2.Dto.Request.RecuperarPasswordRequest;
import prueba.com.example.proyectoalmacen2.Dto.Response.LoginResponse;
import prueba.com.example.proyectoalmacen2.Dto.Response.MensajeResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    MensajeResponse cambiarPasswordPrimerLogin(CambiarPasswordRequest request);
    MensajeResponse recuperarPassword(RecuperarPasswordRequest request);
}