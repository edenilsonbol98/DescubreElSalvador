package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class Mapa_ruta_publicaciones extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseFirestore myRef;
    private LatLng locationLocal;
    private  MarkerOptions  lugarOrigen, lugarDestino = new MarkerOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_ruta_publicaciones);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
            }

    public void marcadorLocal(){
        myRef= FirebaseFirestore.getInstance();
        myRef.collection("publicacion").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Bundle extras = getIntent().getExtras();
                String idPublicacion = Publicaciones.idPubMapa;
                String idActual;
                for (QueryDocumentSnapshot snapshot:task.getResult()) {
                    idActual= snapshot.getId();
                    if (idActual.equals(idPublicacion)) {
                        Map geo;
                        geo = (Map) snapshot.get("lonlan");
                        geo.get("Latitud");


                        break;
                    }
                }
            }
        });

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
       // marcadorLocal();
        lugarOrigen = new MarkerOptions().position(Publicaciones.locationLocal).title("Tú destino");
      //  lugarDestino = new MarkerOptions().position(Publicaciones.locacionUsuario).title("Tú ubicación actual");
        mMap.moveCamera(CameraUpdateFactory.newLatLng(locationLocal));
    }
}
