package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;

import java.util.ArrayList;

import ahmed.easyslider.EasySlider;
import ahmed.easyslider.SliderItem;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.ModeloPublicacion;

public class Publicaciones extends AppCompatActivity {

    private FirebaseFirestore myRef;
    private String url1="", url2="", url3="", url4="";
    private TextView textTitulo, textDescripcion, textFijo, textCelular, textGeo;
    private EasySlider slider;

    private Context mContext;
    private BoomMenuButton boomMenuButton;
    private boolean init = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones);
        myRef=FirebaseFirestore.getInstance();
        textTitulo=findViewById(R.id.pubTxtTitulo);
        textDescripcion=findViewById(R.id.pubTxtDescripcion);
        textCelular=findViewById(R.id.pubTxtCelular);
        textFijo=findViewById(R.id.pubTxtFijo);
        textGeo=findViewById(R.id.txtGeoPointPub);
        slider= findViewById(R.id.pubSlider);
        mostrarPublicaciones();

        mContext = this;
        boomMenuButton = findViewById(R.id.boom);
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
                String urls, idActual;
                for (QueryDocumentSnapshot snapshot:task.getResult()) {
                    idActual= snapshot.getId();
                    if (idActual.equals(idPublicacion)) {
                        textTitulo.setText(snapshot.get("titulo").toString());
                        textDescripcion.setText(snapshot.get("descripcion").toString());
                        textCelular.setText(snapshot.get("celular").toString());
                        textFijo.setText(snapshot.get("telefono").toString());
                        textGeo.setText(snapshot.get("lonlan").toString());
                        urls=snapshot.get("urlFotos").toString();
                        separarUrls(urls);
                        ArrayList<SliderItem> sliderItems = new ArrayList<>();
                        sliderItems.add(new SliderItem("Foto 1",url1));
                        sliderItems.add(new SliderItem("Foto 2",url2));
                        sliderItems.add(new SliderItem("Foto 3",url3));
                        sliderItems.add(new SliderItem("Foto 4",url4));
                        slider.setPages(sliderItems);
                    }
                }
            }
        });

    }

    private void separarUrls(String urls) {
        boolean foto3=false, foto4=false, foto2=false, foto1=true, caracterInvalido=false;
        int contador = urls.length()-1, i=0;
        for (int h=0; h<contador; h++)
        {
            char c = urls.charAt(h);
            String b = String.valueOf(c);
            if (caracterInvalido && !(b.equals(",")) && !(b.equals(" "))) {
                if (foto1) { url1+=b;i=1;}
                else if (foto2) { url2+=b; i=2;}
                else if (foto3) { url3+=b; i=3;}
                else if (foto4) { url4+=b;}
            }
            else{
                if (i==0) {foto1=true;caracterInvalido = true;}
                else if (i == 1) {
                    foto1 = false;
                    foto2 = true;
                } else if (i == 2) {
                    foto2 = false;
                    foto3 = true;
                } else if (i == 3) {
                    foto3 = false;
                    foto4 = true;
                }
            }
        }
    }

    public void GraficarRuta(View view) {
    }

    public void MandarMensajes(View view) {
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
                            Intent llamar = new Intent(Publicaciones.this, CargarLugares.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 1) {
                            Intent llamar = new Intent(Publicaciones.this, Publicaciones.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 2) {
                            Intent llamar = new Intent(Publicaciones.this, AgregarPublicacion.class);
                            startActivity(llamar);
                            finish();
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
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
