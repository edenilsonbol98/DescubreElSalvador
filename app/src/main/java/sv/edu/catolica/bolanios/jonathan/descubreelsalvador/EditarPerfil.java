package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditarPerfil extends AppCompatActivity {
    private Spinner departamento;
    private EditText nombre,apellido,telefono;
    private TextView correo;
    private ImageView perfil;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);


        nombre=findViewById(R.id.edtNombre);
        apellido=findViewById(R.id.edtApellido);
        telefono=findViewById(R.id.edtTelefono);
        correo=findViewById(R.id.textCorreo);
        perfil=findViewById(R.id.imgPerf);
        departamento=findViewById(R.id.spDepartamento);

        String [] opcionesDepartamento={"Ahuachapán","Sonsonate","Santa Ana","San Salvador","Cuscatlán","Cabañas","Chalatenango","La Libertad","La Paz","San Vicente","Morazán","Usulután","San Miguel","La Unión"};

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,opcionesDepartamento);
        departamento.setAdapter(adaptador);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.logo_foreground);

        mostrar();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar, menu);
        return true;
    }

    private void mostrar(){
        String myUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference= FirebaseDatabase.getInstance().getReference("Usuarios").child(myUser);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ReUsuario reUsuario=dataSnapshot.getValue(ReUsuario.class);
                nombre.setText(reUsuario.getNombre());
                apellido.setText(reUsuario.getApellido());
                telefono.setText(reUsuario.getTelefono());
                correo.setText(reUsuario.getCorreo());
                departamento.equals(reUsuario.getDepartamento());
                if(reUsuario.getImageURL().equals("default")){
                    perfil.setImageResource(R.mipmap.ic_launcher);
                }else {
                    Glide.with(EditarPerfil.this).load(reUsuario.getImageURL()).into(perfil);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void actualizar(){
        final String nom =nombre.getText().toString();
        final String apell =apellido.getText().toString();
        final String telef =telefono.getText().toString();
        final String select=departamento.getSelectedItem().toString();
        final String myUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference= FirebaseDatabase.getInstance().getReference("Usuarios").child(myUser);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String,Object> hashMap=new HashMap<>();
                hashMap.put("nombre",nom);
                hashMap.put("apellido",apell);
                hashMap.put("telefono",telef);
                hashMap.put("departamento",select);

                reference.updateChildren(hashMap);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.guardarEdit:
                        actualizar();
                        Toast.makeText(EditarPerfil.this,"Actualizado",Toast.LENGTH_LONG).show();
                        Intent llamar = new Intent(EditarPerfil.this, Perfil.class);
                        startActivity(llamar);
                        finish();

                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


}
