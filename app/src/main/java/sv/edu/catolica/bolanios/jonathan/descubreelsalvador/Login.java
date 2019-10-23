package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void iniciar(View view) {
        Intent intencion1 = new Intent(Login.this, AgregarPublicacion.class);
        startActivity(intencion1);
    }

    public void registrarse(View view) {
        Intent intencion1 = new Intent(Login.this, Registrarse.class);
        startActivity(intencion1);
    }
}
