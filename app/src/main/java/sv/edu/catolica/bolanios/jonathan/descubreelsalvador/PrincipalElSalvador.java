package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class PrincipalElSalvador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_el_salvador);

        Intent llamarMenu = new Intent(getApplicationContext(),MenuSliderActivity.class);
        startActivity(llamarMenu);
    }
}
