package prueba.com.example.proyectoalmacen2.Util;

public final class AppConstants {

    private AppConstants() {
        // Constructor privado para evitar instanciación
    }

    // Módulos para el Historial
    public static final String MODULO_AUTH = "AUTENTICACION";
    public static final String MODULO_USUARIOS = "USUARIOS";
    public static final String MODULO_ALMACEN = "ALMACEN";
    public static final String MODULO_SEGURIDAD = "SEGURIDAD";

    // Acciones para el Historial
    public static final String ACCION_LOGIN = "LOGIN";
    public static final String ACCION_CAMBIO_PASSWORD = "CAMBIO_PASSWORD";
    public static final String ACCION_RECUPERACION_PASSWORD = "RECUPERACION_PASSWORD";
    public static final String ACCION_CREACION_USUARIO = "CREACION_USUARIO";
    public static final String ACCION_EDICION_USUARIO = "EDICION_USUARIO";
    public static final String ACCION_ACTIVACION_USUARIO = "ACTIVACION_USUARIO";
    public static final String ACCION_DESACTIVACION_USUARIO = "DESACTIVACION_USUARIO";
    public static final String ACCION_ELIMINACION_USUARIO = "ELIMINACION_USUARIO";
    public static final String ACCION_REGISTRO_PRODUCTO = "REGISTRO_PRODUCTO";
    public static final String ACCION_EDICION_PRODUCTO = "EDICION_PRODUCTO";

    // Valores por defecto
    public static final int STOCK_MINIMO_DEFAULT = 5;
}