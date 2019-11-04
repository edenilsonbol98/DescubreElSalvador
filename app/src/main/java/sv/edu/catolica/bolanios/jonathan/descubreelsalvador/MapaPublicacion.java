package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.VariablesCompartidas;

public class MapaPublicacion extends FragmentActivity implements GoogleMap.OnMapLongClickListener , OnMapReadyCallback {

    private GoogleMap mMap;
    Location lugar;
    Geocoder decod;
    LatLng move = new LatLng(13.733008, -88.883351);
    public static LatLng LocationExistente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_publicacion);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapLongClickListener(this);
        if (AgregarPublicacion.valorRecibido!=null) {
            Marker mo =mMap.addMarker(new MarkerOptions().position(AgregarPublicacion.valorRecibido).title("Tu ubicación"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AgregarPublicacion.valorRecibido,8));
        }
        else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(move,8));
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        mMap.clear();
        Marker mo =mMap.addMarker(new MarkerOptions().position(latLng).title("Tu ubicación"));
        if (mo!=null) {
            lugar= new Location("location");
            lugar.setLatitude(latLng.latitude);
            lugar.setLongitude(latLng.longitude);
            LocationExistente = new LatLng(latLng.latitude,latLng.longitude);
            mandarLocation(lugar);

        }
        else {

        }
    }

    private void mandarLocation(Location locat) {
        try {
            decod= new Geocoder(MapaPublicacion.this, Locale.getDefault());
            List<Address> direc = decod.getFromLocation(locat.getLatitude(), locat.getLongitude(),1);
            VariablesCompartidas.setDireccion(direc.get(0).getAddressLine(0));
            VariablesCompartidas.setDepartammento(direc.get(0).getAdminArea());
            VariablesCompartidas.setLatitud(locat.getLatitude());
            VariablesCompartidas.setLongitud(locat.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
