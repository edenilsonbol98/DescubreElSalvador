package sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases;

import java.util.ArrayList;

public class Publicacion {
    public String titulo;
    public String descripcion;
    public Localizacion location;
    public String telefono;
    public String fijo;
    public ArrayList<String> fotos;
    public Usuario user;

    public Publicacion(String titulo, String descripcion, Localizacion location, String telefono, String fijo, ArrayList<String> fotos, Usuario user) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.location = location;
        this.telefono = telefono;
        this.fijo = fijo;
        this.fotos = fotos;
        this.user = user;
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
                ", user=" + user +
                '}';
    }

    public Publicacion() {
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

    public String getFijo() {
        return fijo;
    }

    public void setFijo(String fijo) {
        this.fijo = fijo;
    }

    public ArrayList<String> getFotos() {
        return fotos;
    }

    public void setFotos(ArrayList<String> fotos) {
        this.fotos = fotos;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
