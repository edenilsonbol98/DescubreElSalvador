package sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class ModeloPublicacion {
    private String celular;
    private String fijo;
    private String descripcion;
    private String direccionPublicacion;
    private String tipoLocal;
    private String departamento;
    private String titulo;
    private String fotos;
    private GeoPoint locacion;
    private String idPublicacion;
    private ArrayList<String> listFotos;

    public ArrayList<String> getListFotos() {
        return listFotos;
    }

    public void setListFotos(ArrayList<String> listFotos) {
        this.listFotos = listFotos;
    }

    public ModeloPublicacion() {
    }

    public String getFotos() {
        return fotos;
    }

    public void setFotos(String fotos) {
        this.fotos = fotos;
    }

    public String getIdPublicacion() {
        return idPublicacion;
    }

    public void setIdPublicacion(String idPublicacion) {
        this.idPublicacion = idPublicacion;
    }

    public GeoPoint getLocacion() {
        return locacion;
    }

    public void setLocacion(GeoPoint locacion) {
        this.locacion = locacion;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFijo() {
        return fijo;
    }

    public void setFijo(String fijo) {
        this.fijo = fijo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccionPublicacion() {
        return direccionPublicacion;
    }

    public void setDireccionPublicacion(String direccionPublicacion) {
        this.direccionPublicacion = direccionPublicacion;
    }

    public String getTipoLocal() {
        return tipoLocal;
    }

    public void setTipoLocal(String tipoLocal) {
        this.tipoLocal = tipoLocal;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
