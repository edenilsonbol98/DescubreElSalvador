package sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Localizacion {
    private GeoPoint geoPoint;
    private @ServerTimestamp Date timesTamp;
    private Usuario user;

    public Localizacion(GeoPoint geoPoint, Date timesTamp, Usuario user) {
        this.geoPoint = geoPoint;
        this.timesTamp = timesTamp;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Localizacion{" +
                "geoPoint=" + geoPoint +
                ", timesTamp='" + timesTamp + '\'' +
                ", user=" + user +
                '}';
    }

    public Localizacion() {
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public Date getTimesTamp() {
        return timesTamp;
    }

    public void setTimesTamp(Date timesTamp) {
        this.timesTamp = timesTamp;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}
