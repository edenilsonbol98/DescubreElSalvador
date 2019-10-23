package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class Registrarse extends AppCompatActivity {

    private static final String TAG = "bi";
    private EditText nombre,apellido,telefono,usuario,contraseña;
    private Spinner departamento;

    FirebaseDatabase ingreso;
    DatabaseReference referencias;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference("User");

        nombre=findViewById(R.id.etNombre);
        apellido=findViewById(R.id.etApellido);
        telefono=findViewById(R.id.etNumero);
        usuario=findViewById(R.id.etUsuario);
        contraseña=findViewById(R.id.etContraseña);
        departamento=findViewById(R.id.spDepartamento);
        String [] opcionesDepartamento={"Ahuachapán","Sonsonate","Santa Ana","San Salvador","Cuscatlán","Cabañas","Chalatenango","La Libertad","La Paz","San Vicente","Morazán","Usulután","San Miguel","La Unión"};

   ArrayAdapter<String> adaptador = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,opcionesDepartamento);
   departamento.setAdapter(adaptador);

        InicializarFirebase();
    }

    public void InicializarFirebase(){
        FirebaseApp.initializeApp(this);
        ingreso=FirebaseDatabase.getInstance();
        referencias= FirebaseDatabase.getInstance().getReference();
    }

    public void Registrar(View view) {
       final String nom =nombre.getText().toString();
       final String apell =apellido.getText().toString();
        final int telef =Integer.parseInt(telefono.getText().toString()) ;
        final String email =usuario.getText().toString();
        final String password= contraseña.getText().toString();
        final String select=departamento.getSelectedItem().toString();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Registrarse.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                 ReUsuario datos=new ReUsuario();
                 datos.setNombre(nom);
                 datos.setApellido(apell);
                 datos.setTelefono(telef);
                 datos.setUsuario(email);
                 datos.setContraseñ(password);
                 datos.setDepartamento(select);
                    mDatabase.push().setValue(datos);
                    Toast.makeText(Registrarse.this,"Exacto",Toast.LENGTH_SHORT).show();
                }else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(Registrarse.this,"Vamos tu puedes",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}




