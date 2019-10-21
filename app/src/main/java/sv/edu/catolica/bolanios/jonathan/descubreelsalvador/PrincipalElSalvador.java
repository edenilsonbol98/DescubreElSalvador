package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PrincipalElSalvador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_el_salvador);


    }

    public void irSesion(View view) {
        Intent intencion1 = new Intent(PrincipalElSalvador.this, Login.class);
        startActivity(intencion1);
    }
}
