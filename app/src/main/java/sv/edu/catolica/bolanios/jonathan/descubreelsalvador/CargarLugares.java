package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_lugares);

        mAuth = FirebaseAuth.getInstance();
      //  pruebaSalir=findViewById(R.id.btnVermas);
        listFotos=new ArrayList<>();
        listModelo= new ArrayList<>();
        classModelo=new ModeloPublicacion();
        myRef=FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mostrarPublicaciones();
      /*  pruebaSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(CargarLugares.this,PrincipalElSalvador.class));
            }
        });*/


    }
    public void mostrarPublicaciones(){
        myRef.collection("publicacion").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot snapshot:task.getResult()) {
                    classModelo = new ModeloPublicacion();
                    url1="";
                    String urls;
                    classModelo.setTitulo(snapshot.get("titulo").toString());
                    classModelo.setDescripcion(snapshot.get("descripcion").toString());
                    classModelo.setDepartamento(snapshot.get("departamento").toString());
                    classModelo.setTipoLocal(snapshot.get("tipoLocal").toString());
                    classModelo.setIdPublicacion(snapshot.getId());
                    urls=snapshot.get("urlFotos").toString();
                    separarUrls(urls);
                    classModelo.setFotos(url1);
                    listModelo.add(classModelo);
                }
                adapter=new MyAdapterPublicaciones(CargarLugares.this,listModelo);
                recyclerView.setAdapter(adapter);
            }
        });
    }
    /*private void separarUrls(String urls) {
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
    }*/
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

}
