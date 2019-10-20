package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AgregarPublicacion extends AppCompatActivity {

    Context contexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_publicacion);
        contexto= this;
        Button botonAgregarTel = (Button) findViewById(R.id.btnmas1);
        Button botonAgregarcel = (Button) findViewById(R.id.btnagreCel);



        botonAgregarTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Dialogo_telefono(contexto);
            }
        });
        botonAgregarcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Dialogo_telefono(contexto);
            }
        });

    }
}
