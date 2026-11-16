package prueba.com.example.proyectoalmacen2.Exception;

public class UnauthorizedActionException extends RuntimeException {
  public UnauthorizedActionException(String mensaje) {
    super(mensaje);
  }
}