package sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases;

import java.util.ArrayList;

public class Publicacion {
    private String titulo;
    private String descripcion;
    private Localizacion location;
    private String telefono;
    private String fijo;
    private ArrayList<String> fotos;

    public Publicacion() {
    }

    @Override
    public String toString() {
        return "Publicacion{" +
                "titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", location=" + location +
                ", telefono='" + telefono + '\'' +
                ", fijo='" + fijo + '\'' +
                ", fotos=" + fotos +
                '}';
    }

    public Publicacion(String titulo, String descripcion, Localizacion location, String telefono, String fijo, ArrayList<String> fotos) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.location = location;
        this.telefono = telefono;
        this.fijo = fijo;
        this.fotos = fotos;
    }

    public String getFijo() {
        return fijo;
    }

    public void setFijo(String fijo) {
        this.fijo = fijo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Localizacion getLocation() {
        return location;
    }

    public void setLocation(Localizacion location) {
        this.location = location;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public ArrayList<String> getFotos() {
        return fotos;
    }

    public void setFotos(ArrayList<String> fotos) {
        this.fotos = fotos;
    }
}
