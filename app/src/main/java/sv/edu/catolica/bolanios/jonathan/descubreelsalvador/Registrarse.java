package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class Registrarse extends AppCompatActivity {

    private EditText nombre,apellido,telefono,usuario,contraseña;
    private Spinner departamento;

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

        referencias= FirebaseDatabase.getInstance().getReference();

String [] opcionesDepartamento={"Ahuachapán","Sonsonate","Santa Ana","San Salvador","Cuscatlán","Cabañas","Chalatenango","La Libertad","La Paz","San Vicente","Morazán","Usulután","San Miguel","La Unión"};

   ArrayAdapter<String> adaptador = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,opcionesDepartamento);
   departamento.setAdapter(adaptador);
    }

    public void Registrar(View view) {
        String nom =nombre.getText().toString();
        String apell =apellido.getText().toString();
        int telef =Integer.parseInt(telefono.getText().toString()) ;
        String usu =usuario.getText().toString();
        String contra= contraseña.getText().toString();
        String select=departamento.getSelectedItem().toString();

        Map<String, Object> update=new HashMap<>();
        update.put("nombre",nombre);
        update.put("apellido",apellido);
        update.put("telefono",telef);
        update.put("usuario",usuario);
        update.put("contraseña",contraseña);
        update.put("departaento",select);
        referencias.child("Usuario").push().setValue(update);
    }
}
