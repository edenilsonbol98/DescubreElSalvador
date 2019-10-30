package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

public class ReUsuario {
    private String id;
    private String nombre;
    private String Apellido;
    private int Telefono;
    private String usuario;
    private String contraseñ;
    private String departamento;
    private String imagenURL;


    public ReUsuario(String id, String nombre, String apellido, int telefono, String usuario, String contraseñ, String departamento, String imagenURL) {
        this.id = id;
        this.nombre = nombre;
        Apellido = apellido;
        Telefono = telefono;
        this.usuario = usuario;
        this.contraseñ = contraseñ;
        this.departamento = departamento;
        this.imagenURL = imagenURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }

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
