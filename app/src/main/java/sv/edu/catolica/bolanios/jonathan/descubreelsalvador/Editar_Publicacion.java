package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Adaptadores.MyAdapterFotosEdit;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.VariablesCompartidas;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Dialogos.DialogoSimpleFotos;

public class Editar_Publicacion extends AppCompatActivity {
    private Spinner tipo;
    private FirebaseFirestore myRef;
    private String idPub;
    private TextView textTitulo, textDescrip;
    private Button buttonMapa, buttonFotos;
    private String spin, myUser;
    public static String cel, fijo, direccionPub,departamento;
    private ArrayAdapter<String> adaptador;
    public static GeoPoint geoVar, location;
    private ArrayList<String> listFotos;
    private RecyclerView recyclerView;
    private MyAdapterFotosEdit myAdapter;
    private Context contexto;
    private static final int GALLERY_INTENT =1;
    private StorageReference storageReference;
    private int actualizar=0;
    private ImageButton btnFotos;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar__publicacion);
        Inicializar();
        mostrarDatos();
    }

    private void Inicializar() {
        Bundle bundle = this.getIntent().getExtras();
        contexto=this;
        myRef= FirebaseFirestore.getInstance();
        if (bundle!=null) {
            idPub = bundle.get("idPublicacion").toString();
        }
        tipo=findViewById(R.id.spTipoEdit);
        String [] opcionesTipo={"Hospedaje", "Turicentro", "Restaurante"};
        adaptador = new ArrayAdapter<>(Editar_Publicacion.this,android.R.layout.simple_spinner_item,opcionesTipo);
        tipo.setAdapter(adaptador);
        textTitulo = findViewById(R.id.txtnombreEdit);
        textDescrip = findViewById(R.id.txtDescripcionPubEdit);
        buttonMapa = findViewById(R.id.btnMapaEdit);
        buttonFotos = findViewById(R.id.btnagreCelEdit);
        btnFotos = findViewById(R.id.imagenEdit);
        recyclerView=findViewById(R.id.recycler_fotos_edit);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        buttonFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Dialogo_telefono(contexto);
            }
        });
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    private void mostrarDatos() {
        myRef.collection("publicacion").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot snapshot:task.getResult()) {
                    if (idPub.equals(snapshot.getId())) {
                        idPub =snapshot.getId();
                        textDescrip.setText(snapshot.get("descripcion").toString());
                        textTitulo.setText(snapshot.get("titulo").toString());
                        spin = snapshot.get("tipoLocal").toString();
                        cel = snapshot.get("celular").toString();
                        fijo = snapshot.get("telefono").toString();
                        ValorSpinner();
                        geoVar = snapshot.getGeoPoint("lonlan");
                        listFotos = (ArrayList<String>) snapshot.getData().get("urlFotos");
                        location = snapshot.getGeoPoint("lonlan");
                        direccionPub=snapshot.get("direccionPub").toString();
                        departamento=snapshot.get("departamento").toString();
                        break;
                    }
                }
                myAdapter=new MyAdapterFotosEdit(Editar_Publicacion.this,listFotos);
                recyclerView.setAdapter(myAdapter);
            }
        });
    }

    public void MostrarGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar fotos"),GALLERY_INTENT);
    }

    public void  ObtenerIntens(){
        if (cel!=null && location!=null) {
            double longitud = VariablesCompartidas.getLongitud();
            double latitud = VariablesCompartidas.getLatitud();
            cel = VariablesCompartidas.getTelefono();
            fijo = VariablesCompartidas.getFijo();
            location = new GeoPoint(latitud,longitud);
            direccionPub = VariablesCompartidas.getDireccion();
            departamento = VariablesCompartidas.getDepartammento();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GALLERY_INTENT&& resultCode==RESULT_OK) {
            Uri uri = data.getData();
            int numFotos = data.getClipData().getItemCount();
            if (!(numFotos>4)) {
                if (data.getClipData()!=null) {
                    for(int i =0; i <= data.getClipData().getItemCount()-1; i++){
                        ClipData.Item itemClip = data.getClipData().getItemAt(i);
                        uri = itemClip.getUri();
                        SubiendoImagenes(uri);
                    }
                }
            }
            else{
                new DialogoSimpleFotos().show(getSupportFragmentManager(), "DialogoSimpleFotos");
                if (DialogoSimpleFotos.entro) {
                    MostrarGaleria();
                }
            }
        }
    }

    private void SubiendoImagenes(Uri uri) {
        listFotos = new ArrayList<>();
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

    private void ValorSpinner() {
        if (spin.equals("Hospedaje")) {  adaptador.getItem(0);}
        else if (spin.equals("Turicentro")) {  adaptador.getItem(1);}
        else{  adaptador.getItem(2);}
    }

    public void btnMapaEdit(View view) {
        Intent intent = new Intent(Editar_Publicacion.this, MapaPublicacionEdit.class);
        startActivity(intent);
    }

    public void btnTelefonosEdit(View view) {
    }

    public void editarFotos(View view) {
        MostrarGaleria();
    }

    public void btnActualizar(View view) {
        ObtenerIntens();
        ActualizarPublicacion();
    }

    private void ActualizarPublicacion() {
        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
        String date = curFormater.format(Calendar.getInstance().getTime());
        String tipoLocal = tipo.getSelectedItem().toString();
        myUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String,Object> publicacion = new HashMap<>();
        publicacion.put("lonlan",location);
        publicacion.put("titulo",textTitulo.getText().toString());
        publicacion.put("descripcion",textDescrip.getText().toString());
        publicacion.put("telefono",fijo);
        publicacion.put("celular",cel);
        publicacion.put("urlFotos",listFotos);
        publicacion.put("idUsuario",myUser);
        publicacion.put("direccionPub",direccionPub);
        publicacion.put("tipoLocal",tipoLocal);
        publicacion.put("fechaCreacion",date);
        publicacion.put("departamento",departamento);
        myRef.collection("publicacion").document(idPub).set(publicacion).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                StorageReference storageRef = storage.getReference();
                for (String item: listFotos) {
                    StorageReference desertRef = storageRef.child("publicacion/"+item);
                    desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // File deleted successfully
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Uh-oh, an error occurred!
                        }
                    });
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        } );
        //
    }
}
