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

import java.text.SimpleDateFormat;
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
    private String departamento;
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
       // crearMenu();
        boomMenuButton = findViewById(R.id.boom);
        classMenu = new MenuDescurbriendo(AgregarPublicacion.this, boomMenuButton);
        classMenu.crearMenu();
        //classMenu.crearMenu(boomMenuButton);
        //classMenu.instanciar(boomMenuButton,mContext);


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
            departamento = VariablesCompartidas.getDepartammento();
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
                ObtenerIntens();
                SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
                String tipoLocal = tipo.getSelectedItem().toString();
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
                publicacion.put("tipoLocal",tipoLocal);
                publicacion.put("fechaCreacion",curFormater);
                 publicacion.put("departamento",departamento);
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

    public void crearMenu(){
        // Use a param to record whether the boom button has been initialized
        // Because we don't need to init it again when onResume()
        if (init) return;
        init = true;

        Drawable[] subButtonDrawables = new Drawable[7];
        int[] drawablesResource = new int[]{
                R.drawable.agregar,
                R.drawable.comida,
                R.drawable.hotel,
                R.drawable.chat,
                R.drawable.turi,
                R.drawable.acerca


        };
        for (int i = 0; i < 4; i++)
            subButtonDrawables[i] = ContextCompat.getDrawable(this, drawablesResource[i]);

        String[] subButtonTexts = new String[]{"BoomMenuButton", "View source code", "Follow me", "Otra cosa","Otra cosa"};

        int[][] subButtonColors = new int[3][2];
        for (int i = 0; i < 3; i++) {
            subButtonColors[i][1] = ContextCompat.getColor(this, R.color.azul);
            subButtonColors[i][0] = Util.getInstance().getPressedColor(subButtonColors[i][1]);

        }

        // Now with Builder, you can init BMB more convenient
        final BoomMenuButton init = new BoomMenuButton.Builder()

                .addSubButton(ContextCompat.getDrawable(this, R.drawable.acerca), subButtonColors[0], "Acerca de nosotros")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.chat), subButtonColors[0], "Chat")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.agregar), subButtonColors[0], "Agregar")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.comida), subButtonColors[0], "Restaurantes")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.turi), subButtonColors[0], "Turicentros")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.hotel), subButtonColors[0], "Hoteles")


                .button(ButtonType.CIRCLE)
                .boom(BoomType.HORIZONTAL_THROW_2)
                .place(PlaceType.SHARE_6_6)
                .subButtonTextColor(ContextCompat.getColor(this, R.color.Blanco))
                .subButtonsShadow(Util.getInstance().dp2px(1), Util.getInstance().dp2px(1))
                .onSubButtonClick(new BoomMenuButton.OnSubButtonClickListener() {
                    @Override
                    public void onClick(int buttonIndex) {
                        if (buttonIndex == 0) {
                            Intent llamar = new Intent(AgregarPublicacion.this, Publicaciones.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 1) {
                            Intent llamar = new Intent(AgregarPublicacion.this, Publicaciones.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 2) {
                            Intent llamar = new Intent(AgregarPublicacion.this, AgregarPublicacion.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 3) {
                            Intent llamar = new Intent(AgregarPublicacion.this, Publicaciones.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 4) {
                            Intent llamar = new Intent(AgregarPublicacion.this, Publicaciones.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 5) {
                            Intent llamar = new Intent(AgregarPublicacion.this, Publicaciones.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 6) {
                            Intent llamar = new Intent(AgregarPublicacion.this, Perfil.class);
                            startActivity(llamar);
                            finish();
                        }

                    }
                })
                .init(boomMenuButton);
    }

}
