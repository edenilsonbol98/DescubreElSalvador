package sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Mensajes;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.R;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.ReUsuario;

public class AdaptadorUsuario extends RecyclerView.Adapter<AdaptadorUsuario.ViewHolder> {
    private Context mContex;
    private List<ReUsuario> mUsers;

    public AdaptadorUsuario(Context mCont,List<ReUsuario> mUsers){
        this.mUsers=mUsers;
        this.mContex=mCont;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContex).inflate(R.layout.item_usuario,parent,false);
        return new AdaptadorUsuario.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ReUsuario user=mUsers.get(position);
        holder.username.setText(user.getNombre());
        if(user.getImageURL().equals("default")){
            holder.profile_image.setImageResource(R.drawable.usuarion);
        }else {
            Glide.with(mContex).load(user.getImageURL()).into(holder.profile_image);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContex, Mensajes.class);
                intent.putExtra("idUsua",user.getId());
                mContex.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return mUsers.size();

    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username=itemView.findViewById(R.id.username);
            profile_image=itemView.findViewById(R.id.profile_image);
        }
    }

}