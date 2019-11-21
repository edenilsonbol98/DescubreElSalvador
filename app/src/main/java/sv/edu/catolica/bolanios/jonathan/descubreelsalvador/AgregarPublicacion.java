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
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.Localizacion;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.MenuDescurbriendo;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.Publicacion;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.VariablesCompartidas;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Dialogos.DialogoSimpleFotos;

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
    private GeoPoint location=null;
    private String cel, fijo, direcPub,myUser;
    private FirebaseFirestore myRef;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private MenuDescurbriendo classMenu;
    private Spinner tipo;
    private String departamento;
    public static LatLng valorRecibido;
    private TextView txtDireccion, textTel, textFotos;
    private boolean entroTel = false, entroFotos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_publicacion);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.logo_foreground);
        tipo=findViewById(R.id.spTipo);
        String [] opcionesTipo={"Hospedaje", "Turicentro", "Restaurante"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,opcionesTipo);
        tipo.setAdapter(adaptador);
        Inicializacion();


    }

    private void Inicializacion() {
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
                entroTel=true;
            }
        });
        myRef = FirebaseFirestore.getInstance();
        mContext = this;
        boomMenuButton = findViewById(R.id.boom);
        txtDireccion = findViewById(R.id.txtDireccionAdd);
        textFotos = findViewById(R.id.txtFotosAdd);
        textTel = findViewById(R.id.txtTelAdd);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_slider, menu);
        return true;
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

    public void MostrarGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar fotos"),GALLERY_INTENT);
    }
    @Override
    protected void onResume() {
        super.onResume();
       // txtDireccion.setText(direcPub);
        if (direcPub!=null) {
            txtDireccion.setText("✔");
        } else if (entroTel) {
            textTel.setText("✔");
        } else if(entroFotos){
            textFotos.setText("✔");
        }
    }

    private static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
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
                            Intent llamar = new Intent(AgregarPublicacion.this, CargarLugares.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 1) {
                            Intent intentar=new Intent(AgregarPublicacion.this, CargarChats.class);
                            startActivity(intentar);

                        } else if (buttonIndex == 2) {
                            Intent llamar = new Intent(AgregarPublicacion.this, AgregarPublicacion.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 3) {
                            Intent llamar = new Intent(AgregarPublicacion.this, CargarLugares.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 4) {
                            Intent llamar = new Intent(AgregarPublicacion.this, CargarLugares.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 5) {
                            Intent llamar = new Intent(AgregarPublicacion.this, CargarLugares.class);
                            startActivity(llamar);
                            finish();
                        }

                    }
                })
                .init(boomMenuButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.perfil:
                Intent llamar = new Intent(AgregarPublicacion.this, Perfil.class);
                startActivity(llamar);
                break;
            case R.id.salir:
                FirebaseAuth.getInstance().signOut();
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void ObtenerFotos(View view) {
        MostrarGaleria();
        entroFotos=true;
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

    public void btnGuardar(View view) {
        boolean validaciones = true;
        ObtenerIntens();
        if (txtTitulo.getText().toString().isEmpty()&& txtDescrip.getText().toString().isEmpty()) {
            Toast.makeText(AgregarPublicacion.this, "No puede dejar campos vacios", Toast.LENGTH_SHORT).show();
            validaciones= false;
        }
        else if (isNumeric(txtTitulo.getText().toString())|| isNumeric(txtDescrip.getText().toString())) {
            Toast.makeText(AgregarPublicacion.this, "Titulo y/o descripción no pueden ser numeros", Toast.LENGTH_SHORT).show();
            validaciones= false;
        }
        else if (location.getLongitude()== 0.0 && location.getLatitude()==0.0) {
            Toast.makeText(AgregarPublicacion.this, "Tiene que elegir una ubicación", Toast.LENGTH_SHORT).show();
            validaciones= false;
        } else if (listFotos.size()==0) {
            Toast.makeText(AgregarPublicacion.this, "Por favor seleccione 4 fotos", Toast.LENGTH_SHORT).show();
            validaciones= false;
        }
        else if (cel==null && fijo==null) {
            Toast.makeText(AgregarPublicacion.this, "No puede dejar vacio los telefónos", Toast.LENGTH_SHORT).show();
            validaciones= false;
        }
        else if( validaciones) {
            final boolean[] unaVez = {true};
            new CountDownTimer(2000, 1000) {
                public void onFinish() {

                    SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
                    String date = curFormater.format(Calendar.getInstance().getTime());
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
                    publicacion.put("fechaCreacion",date);
                    publicacion.put("departamento",departamento);
                    myRef.collection("publicacion").document().set(publicacion).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AgregarPublicacion.this, "Publicacion hechá", Toast.LENGTH_SHORT).show();
                            Intent cargar=new Intent(AgregarPublicacion.this,CargarLugares.class);
                            startActivity(cargar);
                        }
                    });
                }

                public void onTick(long millisUntilFinished) {
                    if (unaVez[0]) {
                        Toast.makeText(AgregarPublicacion.this, "Obteniendo información", Toast.LENGTH_SHORT).show();
                        unaVez[0] =false;
                    }
                }
            }.start();
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
        if (MapaPublicacion.LocationExistente==null) {
            Intent intent = new Intent(this, MapaPublicacion.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, MapaPublicacion.class);
            valorRecibido= MapaPublicacion.LocationExistente;
            startActivity(intent);
        }
    }

}
