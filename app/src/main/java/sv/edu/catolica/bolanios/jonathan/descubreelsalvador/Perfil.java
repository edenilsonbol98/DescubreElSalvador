package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;


import com.nightonke.boommenu.BoomMenuButton;

import java.util.ArrayList;

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.ModeloPublicacion;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.MyAdapterEditarPublicaciones;


public class Perfil extends AppCompatActivity {
    private Context mContext;
    private BoomMenuButton boomMenuButton;
    private boolean init = false;
    private TextView nombre,departamento,correo,telefono;
    private ImageView perfil,editar;
    DatabaseReference reference;

    private FirebaseFirestore myRef;
    private ArrayList<ModeloPublicacion> listModelo;
    private ModeloPublicacion classModelo;
    private  ArrayList<String> listFotos;
    private RecyclerView recyclerView;
    private MyAdapterEditarPublicaciones adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Inicializar();
        ReUsuario reUsuario=new ReUsuario();

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editar=new Intent(Perfil.this,EditarPerfil.class);
                startActivity(editar);
            }
        });

    }

    private void Inicializar() {
        mContext = this;
        boomMenuButton = (BoomMenuButton)findViewById(R.id.boom);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.logo_foreground);

        nombre=findViewById(R.id.txtUsuarioNombre);
        departamento=findViewById(R.id.txtdepartamento);
        correo=findViewById(R.id.txtCorreo);
        telefono=findViewById(R.id.txtTelefono);
        perfil=findViewById(R.id.imgPerfil);
        editar=findViewById(R.id.btnMensaje);




        myRef=FirebaseFirestore.getInstance();
        listModelo = new ArrayList<>();
        recyclerView=findViewById(R.id.recycler_editar_perfil);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mostrarPublicaciones();
        MostrarPerfil();
    }

    public void mostrarPublicaciones(){
        final String myUser =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        myRef.collection("publicacion").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot snapshot:task.getResult()) {
                    if (myUser.equals(snapshot.get("idUsuario"))) {
                        classModelo = new ModeloPublicacion();
                       // listFotos = new ArrayList<>();
                        classModelo.setTitulo(snapshot.get("titulo").toString());
                        classModelo.setDescripcion(snapshot.get("descripcion").toString());
                        classModelo.setIdPublicacion(snapshot.getId());
                        listFotos = (ArrayList<String>) snapshot.getData().get("urlFotos");
                        classModelo.setFotos(listFotos.get(0));
                        listModelo.add(classModelo);
                    }
                }
                adaptador=new MyAdapterEditarPublicaciones(Perfil.this,listModelo);
                recyclerView.setAdapter(adaptador);
            }
        });
    }

    private void MostrarPerfil(){
        String myUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference= FirebaseDatabase.getInstance().getReference("Usuarios").child(myUser);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ReUsuario reUsuario=dataSnapshot.getValue(ReUsuario.class);
                nombre.setText(reUsuario.getNombre()+" "+reUsuario.getApellido());
                departamento.setText(reUsuario.getDepartamento());
                telefono.setText(reUsuario.getTelefono());
                correo.setText(reUsuario.getCorreo());
                if(reUsuario.getImageURL().equals("default")){
                    perfil.setImageResource(R.drawable.usuarion);
                }else {
                    Glide.with(getApplicationContext()).load(reUsuario.getImageURL()).into(perfil);
                }
                if(reUsuario.getApellido().isEmpty() && reUsuario.getDepartamento().isEmpty() && reUsuario.getTelefono().isEmpty()){
                    editar.setEnabled(false);
                }else{
                    editar.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_slider, menu);
        return true;
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
        new BoomMenuButton.Builder()

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
                            Intent llamar = new Intent(Perfil.this,Publicaciones.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 1) {
                            Intent llamar = new Intent(Perfil.this,CargarChats.class);
                            startActivity(llamar);

                        } else if (buttonIndex == 2) {
                            Intent llamar = new Intent(Perfil.this,AgregarPublicacion.class);
                            startActivity(llamar);
                            finish();
                        }else if (buttonIndex == 3) {
                            Intent llamar = new Intent(Perfil.this,Publicaciones.class);
                            startActivity(llamar);
                            finish();
                        }else if (buttonIndex == 4) {
                            Intent llamar = new Intent(Perfil.this,Publicaciones.class);
                            startActivity(llamar);
                            finish();
                        }else if (buttonIndex == 5) {
                            Intent llamar = new Intent(Perfil.this,Publicaciones.class);
                            startActivity(llamar);
                            finish();
                        }else if (buttonIndex == 6) {
                            Intent llamar = new Intent(Perfil.this,Publicaciones.class);
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
                Intent llamar = new Intent(Perfil.this, Perfil.class);
                startActivity(llamar);
                break;
            case R.id.salir:
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent llamada = new Intent(Perfil.this, PrincipalElSalvador.class);
                startActivity(llamada);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}


