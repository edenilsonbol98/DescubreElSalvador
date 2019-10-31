package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import ahmed.easyslider.EasySlider;
import ahmed.easyslider.SliderItem;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.ModeloPublicacion;

public class Publicaciones extends AppCompatActivity {

    private FirebaseFirestore myRef;
    private String url1="", url2="", url3="", url4="";
    private TextView textTitulo, textDescripcion, textFijo, textCelular, textGeo;
    private EasySlider slider;

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
}
