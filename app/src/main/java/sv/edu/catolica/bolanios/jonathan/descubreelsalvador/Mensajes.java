package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Adaptadores.AdaptadorChat;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.Chat;

public class Mensajes extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;
    ImageButton btn_send;
    EditText text_send;

    FirebaseUser fuser;
    DatabaseReference reference;
    Intent intent;

    AdaptadorChat messageAdapter;
    List<Chat> mChat;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);

        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profile_image=findViewById(R.id.profile_image);
        username=findViewById(R.id.userName);
        btn_send=findViewById(R.id.btn_send);
        text_send=findViewById(R.id.text_send);

        Bundle extras = getIntent().getExtras();
        final String idUser = extras.getString("idUsua");
        Toast.makeText(Mensajes.this,"hola"+idUser+"",Toast.LENGTH_LONG).show();
        fuser= FirebaseAuth.getInstance().getCurrentUser();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg=text_send.getText().toString();
                if(!msg.equals("")){
                    EnviarMensaje(fuser.getUid(),idUser,msg);
                }else {
                    Toast.makeText(Mensajes.this,"Debes escribir un mensaje",Toast.LENGTH_LONG).show();
                }
                text_send.setText("");
            }
        });

        reference= FirebaseDatabase.getInstance().getReference("Usuarios").child(idUser);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ReUsuario reUsuario=dataSnapshot.getValue(ReUsuario.class);
                username.setText(reUsuario.getNombre());
                if(reUsuario.getImageURL().equals("default")){
                    profile_image.setImageResource(R.mipmap.ic_launcher);
                }else {
                    Glide.with(Mensajes.this).load(reUsuario.getImageURL()).into(profile_image);
                }
                leerMensajes(fuser.getUid(),idUser,reUsuario.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private  void EnviarMensaje(String sender,String receiver,String message){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("emisor",sender);
        hashMap.put("receptor",receiver);
        hashMap.put("mensaje",message);

        reference.child("Chats").push().setValue(hashMap);
    }

    private void leerMensajes(final String myid, final String userid, final String imageurl){
        mChat=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Chat chat=dataSnapshot1.getValue(Chat.class);
                    if(chat.getReceptor().equals(myid) && chat.getEmisor().equals(userid) || chat.getReceptor().equals(userid) && chat.getEmisor().equals(myid)){
                        mChat.add(chat);
                    }
                    messageAdapter=new AdaptadorChat(Mensajes.this,mChat,imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
