package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class CargarLugares extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button pruebaSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_lugares);

        mAuth = FirebaseAuth.getInstance();
        pruebaSalir=findViewById(R.id.btnBoton);

        pruebaSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(CargarLugares.this,PrincipalElSalvador.class));
            }
        });
    }


}
