package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class PrincipalElSalvador extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_el_salvador);

        mAuth = FirebaseAuth.getInstance();


    }

    public void irSesion(View view) {
        Intent intencion1 = new Intent(PrincipalElSalvador.this, Login.class);
        startActivity(intencion1);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(PrincipalElSalvador.this,CargarLugares.class));
        }
    }
}
