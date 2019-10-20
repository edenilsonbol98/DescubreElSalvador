package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

public class Listado {
    private int id;
    private String titulo, descripcion, imagen ;
    public Listado(int id, String titulo, String descripcion, String imagen) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagen() {
        return imagen;
    }
}
