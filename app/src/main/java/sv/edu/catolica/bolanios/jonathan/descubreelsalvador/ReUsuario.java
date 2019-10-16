package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

public class ReUsuario {
    private String nombre;
    private String Apellido;
    private int Telefono;
    private String usuario;
    private String contraseñ;
    private String departamento;

    public ReUsuario() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public int getTelefono(int telefo) {
        return Telefono;
    }

    public void setTelefono(int telefono) {
        Telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseñ() {
        return contraseñ;
    }

    public void setContraseñ(String contraseñ) {
        this.contraseñ = contraseñ;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
