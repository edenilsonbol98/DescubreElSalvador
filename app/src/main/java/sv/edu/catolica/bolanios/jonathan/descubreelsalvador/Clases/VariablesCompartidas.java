package sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases;

public class VariablesCompartidas {
    public static String telefono;
    public static String fijo;
    public static double latitud;
    public static double longitud;
    public static String direccion;
    public static String departammento;

    public static String getDepartammento() {
        return departammento;
    }

    public static void setDepartammento(String departammento) {
        VariablesCompartidas.departammento = departammento;
    }

    public static String getDireccion() {
        return direccion;
    }

    public static void setDireccion(String direccion) {
        VariablesCompartidas.direccion = direccion;
    }

    public VariablesCompartidas() {
    }

    public static String getTelefono() {
        return telefono;
    }

    public static void setTelefono(String telefono) {
        VariablesCompartidas.telefono = telefono;
    }

    public static String getFijo() {
        return fijo;
    }

    public static void setFijo(String fijo) {
        VariablesCompartidas.fijo = fijo;
    }

    public static double getLatitud() {
        return latitud;
    }

    public static void setLatitud(double latitud) {
        VariablesCompartidas.latitud = latitud;
    }

    public static double getLongitud() {
        return longitud;
    }

    public static void setLongitud(double longitud) {
        VariablesCompartidas.longitud = longitud;
    }
}
