package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.DataParser;

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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

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
        actualizarRuta();
    }

    private void actualizarRuta() {
        new CountDownTimer(10000, 1000) {
            public void onFinish() {
            }
            public void onTick(long millisUntilFinished) {
                Marcadores();
            }
        }.start();
    }

    private void Marcadores() {
        mMap.getUiSettings().setZoomControlsEnabled(true);
       // Toast.makeText(Mapa_ruta_publicaciones.this, ""+Publicaciones.locacionUsuario, Toast.LENGTH_LONG).show();
        lugarOrigen = new MarkerOptions().position(Publicaciones.locationLocal).title("Tú destino");
        lugarDestino = new MarkerOptions().position(Publicaciones.locationLocal).title("Tú ubicación actual");
        mMap.addMarker(lugarOrigen);
        mMap.addMarker(lugarDestino);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Publicaciones.locationLocal,8));
        Toast.makeText(Mapa_ruta_publicaciones.this, ""+Publicaciones.locationLocal, Toast.LENGTH_LONG).show();
       String objUrl = getUrl(Publicaciones.locacionUsuario, Publicaciones.locationLocal, "driving");
        TaskRequestDirections directions = new TaskRequestDirections();
        directions.execute(objUrl);
    }
    private String getUrl(LatLng position1, LatLng position2, String driving) {
        String origin = "origin="+ position1.latitude+","+position1.longitude;
        String destn = "destination="+ position2.latitude+","+position2.longitude;
        String mode = "mode="+driving;
        String parametets = origin+"&"+destn+"&"+mode;
        String key = "AIzaSyAC06WdykiFYrc8vuULq3hWlk_J3xrH0jM";
        // String key = "AIzaSyDMHvpJg30IDQF3nuZGHH31zT5G1Qold4M";
        String url = "https://maps.googleapis.com/maps/api/directions/json?"+parametets+"&key="+key;
        return url;
    }

    private String getPoints(String SourceUrl) throws IOException {
        String responseStr="";
        InputStream inputStream=null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(SourceUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedInputStream = new BufferedReader(streamReader);

            StringBuffer stringBuffer = new StringBuffer();
            String line="";
            while ((line=bufferedInputStream.readLine())!=null){
                stringBuffer.append(line);
            }
            responseStr = stringBuffer.toString();
            bufferedInputStream.close();
            streamReader.close();
        } catch (Exception e) {
        }
        finally {
            if (inputStream!=null) {
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseStr;
    }

    public class TaskRequestDirections extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String responseStr="";
            try {
                responseStr = getPoints(strings[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseStr;
        }

        @Override
        protected void onPostExecute(String s) {
           TaskParse taskParse = new TaskParse();
           taskParse.execute(s);
        }
    }

    public class TaskParse extends AsyncTask<String,Void,List<List<HashMap<String, String>>>>{

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes=null;
            try {
                jsonObject= new JSONObject(strings[0]);
                DataParser clasPars = new DataParser();
                routes = clasPars.parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            ArrayList points = null;
            PolylineOptions polylineOptions = null;
            for (List<HashMap<String, String>> item1 : lists) {
                points = new ArrayList();
                polylineOptions = new PolylineOptions();
                for (HashMap<String, String> item2: item1) {
                    double lat = Double.parseDouble(item2.get("lat"));
                    double lon = Double.parseDouble(item2.get("lng"));
                    points.add(new LatLng(lat,lon));
                }
                polylineOptions.addAll(points);
                polylineOptions.width(15);
                polylineOptions.color(Color.BLUE);
                polylineOptions.geodesic(true);
            }
            if (polylineOptions!=null) {
                mMap.addPolyline(polylineOptions);
            }
        }
    }
}
