package sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Adaptadores.AdaptadorUsuario;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.Chat;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.R;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.ReUsuario;


public class FragmentChat extends Fragment {



    private RecyclerView recyclerView;
    private AdaptadorUsuario userAdapter;
    private List<ReUsuario> mUsers;

    FirebaseUser fuser;
    DatabaseReference reference;

    private List<String> usersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fragment_chat,container,false);

        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser= FirebaseAuth.getInstance().getCurrentUser();
        usersList=new ArrayList<>();

        reference= FirebaseDatabase.getInstance().getReference("Chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Chat chat=snapshot.getValue(Chat.class);

                    if(chat.getEmisor().equals(fuser.getUid())){
                        usersList.add(chat.getReceptor());
                    }
                    if(chat.getReceptor().equals(fuser.getUid())){
                        usersList.add(chat.getEmisor());
                    }

                }
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void readChats(){
        mUsers=new ArrayList<>();
        reference=FirebaseDatabase.getInstance().getReference("Usuarios");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // mUsers.clear();
                int i=0;
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    ReUsuario user=snapshot.getValue(ReUsuario.class);
                    for (String id:usersList){

                        if(mUsers.size()>0 && mUsers.size()==i+1)
                        {
                            if(!user.getId().equals(mUsers.get(i).getId()))
                            {
                                if(user.getId().equals(id)){

                                    mUsers.add(user);

                                }
                                i++;
                            }
                        }
                        else
                        {
                            if(user.getId().equals(id)){

                                mUsers.add(user);
                            }
                        }
                    }
                }
                userAdapter=new AdaptadorUsuario(getContext(),mUsers);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
