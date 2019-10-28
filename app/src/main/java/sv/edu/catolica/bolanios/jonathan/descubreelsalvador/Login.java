package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.Publicacion;

public class Login extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static int PERMISSION_GRANTED;
    private FirebaseAuth mAuth;
    private Button ingresar;
    private EditText txtCorreo,txtpass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // verificarPermisos();
        Intent intencion1 = new Intent(Login.this, Publicaciones.class);
        startActivity(intencion1);
        finish();

        /* mAuth = FirebaseAuth.getInstance();

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

                     }else {
                         Toast.makeText(Login.this,"Las credenciales no son las correctas verifique si la contraseña o su correo son conrrectos",Toast.LENGTH_LONG).show();
                     }

                 }
            });
             }
         });

        */

    }






}
