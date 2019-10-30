package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.Localizacion;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.MenuDescurbriendo;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.Publicacion;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.VariablesCompartidas;

public class AgregarPublicacion extends AppCompatActivity {
    private Context mContext;
    private BoomMenuButton boomMenuButton;
    private boolean init = false;


    Context contexto;
    private Button botonAgregarFotos;
    private StorageReference storageReference;
    private static final int GALLERY_INTENT =1;
    private EditText txtDescrip,txtTitulo;
    private List<String> listFotos= new ArrayList<>();
    private GeoPoint location;
    private String cel, fijo, direcPub,myUser;
    private FirebaseFirestore myRef;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private MenuDescurbriendo classMenu;
    private Spinner tipo;

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

        mContext = this;
        //classMenu = new MenuDescurbriendo(AgregarPublicacion.this);
        //classMenu.crearMenu(boomMenuButton);
        //classMenu.instanciar(boomMenuButton,mContext);
       // boomMenuButton = (BoomMenuButton)findViewById(R.id.boom);

        tipo=findViewById(R.id.spTipo);

        String [] opcionesTipo={"Hospedaje", "Turicentro", "Restaurante"};

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,opcionesTipo);
        tipo.setAdapter(adaptador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_slider, menu);
        return true;
    }

    private void prueba() {
        //Button botonAgregarcel = (Button) findViewById(R.id.btnagreCel);
       /* botonAgregarTel.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                new Dialogo_telefono(contexto);
            }
        });*/

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
    }

    public void ObtenerFotos(View view) {
        if (!(txtTitulo.getText().toString().isEmpty()&& txtDescrip.getText().toString().isEmpty())) {
            Toast.makeText(AgregarPublicacion.this, "Elija solo 4 fotos", Toast.LENGTH_SHORT).show();
            MostrarGaleria();
        }
        else {
            //mensaje
        }
    }

    public void  ObtenerIntens(){
            double longitud = VariablesCompartidas.getLongitud();
            double latitud = VariablesCompartidas.getLatitud();
            cel = VariablesCompartidas.getTelefono();
            fijo = VariablesCompartidas.getFijo();
            location = new GeoPoint(latitud,longitud);
            direcPub = VariablesCompartidas.getDireccion();
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
                Toast.makeText(AgregarPublicacion.this, "Imagenen subida", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void SubiendoImagenes(Uri uri) {
        final StorageReference filePatch = storageReference.child("publicacion").child(uri.getLastPathSegment());
        filePatch.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
               // listFotos.add(filePatch.getDownloadUrl().getResult().toString());
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
        new CountDownTimer(30000, 1000) {
            public void onFinish() {
                // When timer is finished
                // Execute your code here
                ObtenerIntens();
                myUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Map<String,Object> publicacion = new HashMap<>();
                publicacion.put("lonlan",location);
                publicacion.put("titulo",txtTitulo.getText().toString());
                publicacion.put("descripcion",txtDescrip.getText().toString());
                publicacion.put("telefono",fijo);
                publicacion.put("celular",cel);
                publicacion.put("urlFotos",listFotos);
                publicacion.put("idUsuario",myUser);
                publicacion.put("direccionPub",direcPub);
                //publicacion.put("tipoLocal",direcPub);
                // publicacion.put("fechaCreacion",direcPub);
                // publicacion.put("departamento",direcPub);
                myRef.collection("publicacion").document().set(publicacion).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AgregarPublicacion.this, "Publicacion hechá", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            public void onTick(long millisUntilFinished) {
                Toast.makeText(AgregarPublicacion.this, "Obteniendo información", Toast.LENGTH_SHORT).show();
            }
        }.start();

    }



}
