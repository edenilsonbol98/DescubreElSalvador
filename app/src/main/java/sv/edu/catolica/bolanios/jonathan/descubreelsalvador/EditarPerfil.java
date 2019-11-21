package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditarPerfil extends AppCompatActivity {
    private Spinner departamento;
    private EditText nombre,apellido,telefono;
    private TextView correo,cambio;
    private ImageView perfil;
    DatabaseReference reference;
    private StorageReference mStorage;
    private FirebaseAuth mAuth;
    private static final int GALLERY_INTENT=1;
    private String listFotos;
    private String myUser;


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
        cambio=findViewById(R.id.cambioDefoto);
        myUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference= FirebaseDatabase.getInstance().getReference("Usuarios").child(myUser);
        mStorage= FirebaseStorage.getInstance().getReference();
        String [] opcionesDepartamento={"Ahuachapán","Sonsonate","Santa Ana","San Salvador","Cuscatlán","Cabañas","Chalatenango","La Libertad","La Paz","San Vicente","Morazán","Usulután","San Miguel","La Unión"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,opcionesDepartamento);
        departamento.setAdapter(adaptador);
        mostrar();
        cambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccione una foto"),GALLERY_INTENT);
                intent = new Intent();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar, menu);
        return true;
    }

    private void mostrar(){
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
                    perfil.setImageResource(R.drawable.usuarion);
                }else {
                    Glide.with(getApplicationContext()).load(reUsuario.getImageURL()).into(perfil);
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
        final String select = departamento.getSelectedItem().toString();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String,Object> hashMap=new HashMap<>();
                hashMap.put("nombre",nom);
                hashMap.put("apellido",apell);
                hashMap.put("telefono",telef);
                hashMap.put("departamento", select);
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
                        Toast.makeText(EditarPerfil.this,"Actualizado con éxito",Toast.LENGTH_LONG).show();
                        //finish();
                         System.exit(0);
                        Intent llamar = new Intent(EditarPerfil.this, Perfil.class);
                        startActivity(llamar);

                break;
            case R.id.cancelarEdit:
                Intent llamar2 = new Intent(EditarPerfil.this, Perfil.class);
                startActivity(llamar2);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode==RESULT_OK){
            final Uri uri=data.getData();
            final StorageReference filePath=mStorage.child("usuario").child(uri.getLastPathSegment());
            filePath.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUrl = task.getResult();
                        listFotos=downloadUrl.toString();//aqui obtener la url de la foto
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                HashMap<String,Object> hashMap=new HashMap<>();
                                hashMap.put("imageURL",listFotos);
                                reference.updateChildren(hashMap);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            });

        }
    }

}
