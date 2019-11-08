package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
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

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.ModeloPublicacion;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.MyAdapterPublicaciones;

public class CargarLugares extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button pruebaSalir;
    ArrayList<String> listFotos;
    ArrayList<ModeloPublicacion> listModelo;
    FirebaseFirestore myRef;
    RecyclerView recyclerView;
    ModeloPublicacion classModelo;
    MyAdapterPublicaciones adapter;
    String url1;

    private Context mContext;
    private BoomMenuButton boomMenuButton;
    private boolean init = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_lugares);
        mAuth = FirebaseAuth.getInstance();
        listFotos=new ArrayList<>();
        listModelo= new ArrayList<>();
        classModelo=new ModeloPublicacion();
        myRef=FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mostrarPublicaciones();
        mContext = this;
        boomMenuButton = findViewById(R.id.boom);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.logo_foreground);

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
                for (QueryDocumentSnapshot snapshot:task.getResult()) {
                    classModelo = new ModeloPublicacion();
                    classModelo.setTitulo(snapshot.get("titulo").toString());
                    classModelo.setDescripcion(snapshot.get("descripcion").toString());
                    classModelo.setDepartamento(snapshot.get("departamento").toString());
                    classModelo.setTipoLocal(snapshot.get("tipoLocal").toString());
                    classModelo.setIdPublicacion(snapshot.getId());
                    listFotos = (ArrayList<String>) snapshot.getData().get("urlFotos");
                    classModelo.setFotos(listFotos.get(0));
                    listModelo.add(classModelo);
                }
                adapter=new MyAdapterPublicaciones(CargarLugares.this,listModelo);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void separarUrls(String urls) {
        boolean  caracterInvalido=false;
        int contador = urls.length()-1;
        for (int h=0; h<contador; h++)
        {
            char c = urls.charAt(h);
            String b = String.valueOf(c);
            if (caracterInvalido && !(b.equals(","))) { url1+=b; }
            else if (b.equals(",")) {break; }
            else { caracterInvalido = true; }
        }
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

                .addSubButton(ContextCompat.getDrawable(this, R.drawable.acerca), subButtonColors[0], "Acerca de nosotros")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.chat), subButtonColors[0], "Chat")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.agregar), subButtonColors[0], "Agregar")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.comida), subButtonColors[0], "Restaurantes")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.turi), subButtonColors[0], "Turicentros")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.hotel), subButtonColors[0], "Hoteles")


                .button(ButtonType.CIRCLE)
                .boom(BoomType.HORIZONTAL_THROW_2)
                .place(PlaceType.SHARE_6_6)
                .subButtonTextColor(ContextCompat.getColor(this, R.color.Blanco))
                .subButtonsShadow(Util.getInstance().dp2px(1), Util.getInstance().dp2px(1))
                .onSubButtonClick(new BoomMenuButton.OnSubButtonClickListener() {
                    @Override
                    public void onClick(int buttonIndex) {
                        if (buttonIndex == 0) {
                            Intent llamar = new Intent(CargarLugares.this, CargarLugares.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 1) {
                            Intent llamar = new Intent(CargarLugares.this, CargarChats.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 2) {
                            Intent llamar = new Intent(CargarLugares.this, AgregarPublicacion.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 3) {
                            Intent llamar = new Intent(CargarLugares.this, CargarLugares.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 4) {
                            Intent llamar = new Intent(CargarLugares.this, CargarLugares.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 5) {
                            Intent llamar = new Intent(CargarLugares.this, CargarLugares.class);
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
                Intent llamar = new Intent(CargarLugares.this, Perfil.class);
                startActivity(llamar);
                break;
            case R.id.salir:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent llamada = new Intent(CargarLugares.this, PrincipalElSalvador.class);
                startActivity(llamada);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

}
