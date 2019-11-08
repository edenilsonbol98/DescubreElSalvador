package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class Registrarse extends AppCompatActivity {

    private static final String TAG = "bi";
    private EditText nombre,apellido,telefono,usuario,contraseña;
    private Spinner departamento;
    private Button Registrarse;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        mAuth = FirebaseAuth.getInstance();

        nombre=findViewById(R.id.etNombre);
        apellido=findViewById(R.id.etApellido);
        telefono=findViewById(R.id.etNumero);
        usuario=findViewById(R.id.etUsuario);
        contraseña=findViewById(R.id.etContraseña);
        departamento=findViewById(R.id.spDepartamento);

        String [] opcionesDepartamento={"Ahuachapán","Sonsonate","Santa Ana","San Salvador","Cuscatlán","Cabañas","Chalatenango","La Libertad","La Paz","San Vicente","Morazán","Usulután","San Miguel","La Unión"};

   ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,opcionesDepartamento);
   departamento.setAdapter(adaptador);
   Registrarse=findViewById(R.id.btnRegistrarse);

     Registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         final String nom =nombre.getText().toString();
           final String apell =apellido.getText().toString();
           final String telef =telefono.getText().toString();
           final String email =usuario.getText().toString();
           final String password= contraseña.getText().toString();
           final String select=departamento.getSelectedItem().toString();

           if(TextUtils.isEmpty(nom) || TextUtils.isEmpty(apell) || TextUtils.isEmpty(telef) || TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)){
               Toast.makeText(Registrarse.this,"Debes de completar los campos",Toast.LENGTH_LONG).show();
           }else if (password.length()<6){
               Toast.makeText(Registrarse.this,"Tu contraseña debe ser mayor a 6 caracteres",Toast.LENGTH_LONG).show();
           }else {
                registrar(nom,apell,telef,email,password,select);
           }



       }
   });

       }

       public void limpiar(){
        nombre.setText("");
        apellido.setText("");
        telefono.setText("");
        usuario.setText("");
        contraseña.setText("");
       }

       private void registrar(final String nombre, final String apellido, final String telefono, final String usuario, String contra, final String departamento){
           mAuth.createUserWithEmailAndPassword(usuario,contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       FirebaseUser firebaseUser=mAuth.getCurrentUser();
                       assert firebaseUser != null;
                       String id=firebaseUser.getUid();
                       reference= FirebaseDatabase.getInstance().getReference("Usuarios").child(id);

                       HashMap<String,String> hashMap=new HashMap<>();
                       hashMap.put("id",id);
                       hashMap.put("nombre",nombre);
                       hashMap.put("apellido",apellido);
                       hashMap.put("telefono",telefono);
                       hashMap.put("correo",usuario);
                       hashMap.put("departamento",departamento);
                       hashMap.put("imageURL","default");

                       reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task2) {
                               if(task2.isSuccessful()){
                                   Intent intent=new Intent(Registrarse.this,Login.class);
                                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                   Toast.makeText(Registrarse.this,"Almacenado con éxito",Toast.LENGTH_LONG).show();
                                   startActivity(intent);
                                   finish();
                               }
                           }
                       });
                   }else {
                       Toast.makeText(Registrarse.this,"No quiere",Toast.LENGTH_LONG).show();
                   }
               }
           });


       }

}
