package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Adaptadores.MyAdapterFotosPantallaCompleta;

public class Fotos_Pantalla_Completa extends AppCompatActivity {
    RecyclerView recyclerView;
    private MyAdapterFotosPantallaCompleta adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos__pantalla__completa);
        Inicializar();
    }

    private void Inicializar() {
        Bundle datos = this.getIntent().getExtras();
        ArrayList<String> fotos = (ArrayList<String>) datos.get("listFotos");
        recyclerView=findViewById(R.id.my_recycler_Fotos_Completa);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adaptador = new MyAdapterFotosPantallaCompleta(Fotos_Pantalla_Completa.this, fotos);
        recyclerView.setAdapter(adaptador);
    }
}
