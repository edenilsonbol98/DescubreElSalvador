package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void registro(View view) {
        Intent signid = new Intent(MainActivity.this,Registrarse.class);
        startActivity(signid);
    }

    public void logueo(View view) {

    }
}
