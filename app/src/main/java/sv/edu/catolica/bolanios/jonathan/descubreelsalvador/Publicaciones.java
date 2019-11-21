package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ahmed.easyslider.EasySlider;
import ahmed.easyslider.SliderItem;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Adaptadores.MyAdapterFotos;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.ModeloPublicacion;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.MyAdapterPublicaciones;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.Publicacion;

public class Publicaciones extends AppCompatActivity {

    private FirebaseFirestore myRef;
    private TextView textTitulo, textDescripcion, textFijo, textCelular, textGeo,usuarioPub;
    private ArrayList<String> listFotos;
    private Button bandeja;
    private FusedLocationProviderClient fusedLocationClient;
    private Context mContext;
    private BoomMenuButton boomMenuButton;
    private boolean init = false;
    private RecyclerView recyclerView;
    private MyAdapterFotos adapterFotos;
    public static LatLng locacionUsuario, locationLocal;
    public static String idPubMapa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones);
        Inicializar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.logo_foreground);
    }

    private void Inicializar() {
        myRef= FirebaseFirestore.getInstance();
        textTitulo=findViewById(R.id.pubTxtTitulo);
        textDescripcion=findViewById(R.id.pubTxtDescripcion);
        textCelular=findViewById(R.id.pubTxtCelular);
        textFijo=findViewById(R.id.pubTxtFijo);
        textGeo=findViewById(R.id.txtGeoPointPub);
        usuarioPub=findViewById(R.id.txtIdUsuarioPub);
        listFotos= new ArrayList<>();
        recyclerView=findViewById(R.id.recycler_fotos);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        mostrarPublicaciones();
        mContext = this;
        boomMenuButton = findViewById(R.id.boom);
       // fusedLocationClient=null;

    }

    private void ActualizandoUbicacion() {
        new CountDownTimer(900000, 240000) {
            public void onFinish() {
            }
            public void onTick(long millisUntilFinished) {
                extraerLocacion();
            }
        }.start();
    }

    private void extraerLocacion() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(Publicaciones.this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(Publicaciones.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        try {
                            if (location != null) {
                                locacionUsuario =new LatLng( location.getLatitude(), location.getLongitude());
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Error "+e.getMessage(),Toast.LENGTH_LONG);
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_slider, menu);
        return true;
    }

    public void mostrarPublicaciones(){
        myRef.collection("publicacion").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Bundle extras = getIntent().getExtras();
                String idPublicacion = extras.getString("idPublicacion");
                idPubMapa= idPublicacion;
                String idActual;
                for (QueryDocumentSnapshot snapshot:task.getResult()) {
                    idActual= snapshot.getId();
                    if (idActual.equals(idPublicacion)) {
                        textTitulo.setText(snapshot.get("titulo").toString());
                        textDescripcion.setText(snapshot.get("descripcion").toString());
                        textCelular.setText(snapshot.get("celular").toString());
                        textFijo.setText(snapshot.get("telefono").toString());
                        textGeo.setText(snapshot.get("lonlan").toString());
                        GeoPoint geoVar = snapshot.getGeoPoint("lonlan");
                        locationLocal = new LatLng(geoVar.getLatitude(),geoVar.getLongitude());
                        usuarioPub.setText(snapshot.get("idUsuario").toString());
                        listFotos = (ArrayList<String>) snapshot.getData().get("urlFotos");
                        break;
                    }
                }
                adapterFotos=new MyAdapterFotos(Publicaciones.this,listFotos);
                recyclerView.setAdapter(adapterFotos);
                ActualizandoUbicacion();
            }
        });

    }

    public void GraficarRuta(View view) {
        Intent intent = new Intent(this, Mapa_ruta_publicaciones.class);
        startActivity(intent);
    }

    public void MandarMensajes(View view) {
        String usuario=usuarioPub.getText().toString();
        Intent intención=new Intent(Publicaciones.this,Mensajes.class);
        intención.putExtra("idUsua",usuario);
        startActivity(intención);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // Use a param to record whether the boom button has been initialized
        // Because we don't need to init it again when onResume()
        if (init) return;
        init = true;

        Drawable[] subButtonDrawables = new Drawable[7];
        int[] drawablesResource = new int[]{
                R.drawable.agregar,
                R.drawable.comida,
                R.drawable.hotel,
                R.drawable.chat,
                R.drawable.turi,
                R.drawable.acerca


        };
        for (int i = 0; i < 4; i++)
            subButtonDrawables[i] = ContextCompat.getDrawable(this, drawablesResource[i]);

        String[] subButtonTexts = new String[]{"BoomMenuButton", "View source code", "Follow me", "Otra cosa","Otra cosa"};

        int[][] subButtonColors = new int[3][2];
        for (int i = 0; i < 3; i++) {
            subButtonColors[i][1] = ContextCompat.getColor(this, R.color.azul);
            subButtonColors[i][0] = Util.getInstance().getPressedColor(subButtonColors[i][1]);

        }

        // Now with Builder, you can init BMB more convenient
        final BoomMenuButton init = new BoomMenuButton.Builder()

                .addSubButton(ContextCompat.getDrawable(this, R.drawable.agregar), subButtonColors[0], "Agregar")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.chat), subButtonColors[0], "Chat")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.acerca), subButtonColors[0], "Acerca de nosotros")


                .button(ButtonType.CIRCLE)
                .boom(BoomType.HORIZONTAL_THROW_2)
                .place(PlaceType.SHARE_3_1)
                .subButtonTextColor(ContextCompat.getColor(this, R.color.Blanco))
                .subButtonsShadow(Util.getInstance().dp2px(1), Util.getInstance().dp2px(1))
                .onSubButtonClick(new BoomMenuButton.OnSubButtonClickListener() {
                    @Override
                    public void onClick(int buttonIndex) {
                        if (buttonIndex == 0) {
                            Intent llamar = new Intent(Publicaciones.this, AgregarPublicacion.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 1) {
                            Intent intentar=new Intent(Publicaciones.this, CargarChats.class);
                            startActivity(intentar);

                        } else if (buttonIndex == 2) {
                            Intent llamar = new Intent(Publicaciones.this, acerca.class);
                            startActivity(llamar);

                        } else if (buttonIndex == 3) {
                            Intent llamar = new Intent(Publicaciones.this, CargarLugares.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 4) {
                            Intent llamar = new Intent(Publicaciones.this, CargarLugares.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 5) {
                            Intent llamar = new Intent(Publicaciones.this, CargarLugares.class);
                            startActivity(llamar);
                            finish();
                        }

                    }
                })
                .init(boomMenuButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.perfil:
                Intent llamar = new Intent(Publicaciones.this, Perfil.class);
                startActivity(llamar);
                break;
            case R.id.salir:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent llamada = new Intent(Publicaciones.this, PrincipalElSalvador.class);
                startActivity(llamada);
                finish();

                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
