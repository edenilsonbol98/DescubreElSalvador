package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.Localizacion;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.Publicacion;

public class AgregarPublicacion extends AppCompatActivity {

    Context contexto;
    private Button botonAgregarFotos;
    private StorageReference storageReference;
    private static final int GALLERY_INTENT =1;
    private EditText txtDescrip;
    private EditText txtTitulo;
    List<String> listFotos= new ArrayList<>();
    private static final String TAG = "MainActivity";
    GeoPoint location;
    Localizacion clasLocalizacion;
    Publicacion classPublic;
    String cel, fijo;
    private FirebaseFirestore myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_publicacion);
        contexto= this;
        storageReference = FirebaseStorage.getInstance().getReference();
        txtDescrip = findViewById(R.id.txtDescripcion);
        txtTitulo= findViewById(R.id.txtnombre);
        botonAgregarFotos =findViewById(R.id.btnFotos);
        Button botonAgregarcel = (Button) findViewById(R.id.btnagreCel);
        botonAgregarcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Dialogo_telefono(contexto);
            }
        });
        myRef = FirebaseFirestore.getInstance();
    }

    private void prueba() {
        //Button botonAgregarcel = (Button) findViewById(R.id.btnagreCel);
       /* botonAgregarTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Dialogo_telefono(contexto);
            }
        });*/

    }

    public void ObtenerFotos(View view) {
        if (!(txtTitulo.getText().toString().isEmpty()&& txtDescrip.getText().toString().isEmpty())) {
            MostrarGaleria();
        }
        else {
            //mensaje
        }
    }

    public void  ObtenerIntens(){
        Bundle data= getIntent().getExtras();
        double longitud = data.getDouble("latitudIntent");
        double latitud = data.getDouble("latitudIntent");
         cel = data.getString("telefono");
         fijo = data.getString("fijo");
        location = new GeoPoint(latitud,longitud);
    }
    private void MostrarGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar fotos"),GALLERY_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GALLERY_INTENT&& resultCode==RESULT_OK) {
            Uri uri = data.getData();
            if (data.getClipData()!=null) {
                for(int i =0; i <= data.getClipData().getItemCount()-1; i++){
                    ClipData.Item itemClip = data.getClipData().getItemAt(i);
                    uri = itemClip.getUri();
                    SubiendoImagenes(uri);
                }
                Toast.makeText(AgregarPublicacion.this, "Imagenes subidas", Toast.LENGTH_LONG).show();
            }
            else if (data.getData()!=null) {

                SubiendoImagenes(uri);
                Toast.makeText(AgregarPublicacion.this, "Imagenen subidas", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void SubiendoImagenes(Uri uri) {
        final StorageReference filePatch = storageReference.child("fotos").child(uri.getLastPathSegment());
        filePatch.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
               // if(!task.isSuccessful()){

                //}
                // Toast.makeText(AgregarPublicacion.this, "Fotos"+ filePatch.getActiveDownloadTasks().toString(), Toast.LENGTH_LONG).show();
                return filePatch.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Uri downloadUrl = task.getResult();
                    listFotos.add(downloadUrl.toString());
                }
            }
        });
    }

    public void MostrarMapa(View view) {
        Intent intent = new Intent(this, MapaPublicacion.class);
        startActivity(intent);
    }

    public void btnGuardar(View view) {
        ObtenerIntens();
        clasLocalizacion.setGeoPoint(location);
        clasLocalizacion.setTimesTamp(null);
        classPublic.setTitulo(txtTitulo.getText().toString());
        classPublic.setDescripcion(txtDescrip.getText().toString());
        classPublic.setLocation(clasLocalizacion);
        classPublic.setTelefono(cel);
        classPublic.setFijo(fijo);
        classPublic.setFotos((ArrayList<String>) listFotos);
       if (classPublic !=null) {
            DocumentReference publicacionesRef = myRef.collection("Publicaciones").document(FirebaseAuth.getInstance().getUid());
            publicacionesRef.set(classPublic).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        //mensaje
                    }
                }
            });
        }

    }
}
