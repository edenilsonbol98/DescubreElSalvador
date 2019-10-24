package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button ingresar;
    private EditText txtCorreo,txtpass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         mAuth = FirebaseAuth.getInstance();

         txtCorreo=findViewById(R.id.etNombre);
         txtpass=findViewById(R.id.etContraseña);

         ingresar=findViewById(R.id.btnLogin);

         ingresar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                String email=txtCorreo.getText().toString();
                String password=txtpass.getText().toString();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                 @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                     if(task.isSuccessful()){
                         Intent intencion1 = new Intent(Login.this, AgregarPublicacion.class);
                         startActivity(intencion1);
                         finish();
                     }else {
                         Toast.makeText(Login.this,"Las credenciales no son las correctas verifique si la contraseña o su correo son conrrectos",Toast.LENGTH_LONG).show();
                     }

                 }
            });
             }
         });



    }



    public void registrarse(View view) {
        Intent intencion1 = new Intent(Login.this, Registrarse.class);
        startActivity(intencion1);
        finish();
    }


}
