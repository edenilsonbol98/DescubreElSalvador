package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Registrarse extends AppCompatActivity {

    private EditText nombre,apellido,telefono,usuario,contraseña;
    private Spinner departamento;

    FirebaseDatabase ingreso;
    DatabaseReference referencias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

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
        String nom =nombre.getText().toString();
        String apell =apellido.getText().toString();
        int telef =Integer.parseInt(telefono.getText().toString()) ;
        String usu =usuario.getText().toString();
        String contra= contraseña.getText().toString();
        String select=departamento.getSelectedItem().toString();

        ReUsuario agregar=new ReUsuario();
        agregar.setNombre(nom);
        agregar.setApellido(apell);
        agregar.setTelefono(telef);
        agregar.setUsuario(usu);
        agregar.setContraseñ(contra);
        agregar.setDepartamento(select);
        referencias.child("Usuario").push().setValue(agregar);

        Toast.makeText(Registrarse.this,"Agregado",Toast.LENGTH_LONG).show();



    }
}
