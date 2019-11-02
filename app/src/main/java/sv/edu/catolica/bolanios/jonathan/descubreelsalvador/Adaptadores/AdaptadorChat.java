package sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.Chat;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Mensajes;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.R;

public class AdaptadorChat extends RecyclerView.Adapter<AdaptadorChat.ViewHolder> {
    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    private Context mContex;
    private List<Chat> mChat;
    private String imageurl;

    FirebaseUser fuser;

    public AdaptadorChat(Context mContex,List<Chat> mChat,String imageurl){
        this.mChat=mChat;
        this.mContex=mContex;
        this.imageurl=imageurl;

    }

    @NonNull
    @Override
    public AdaptadorChat.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_TYPE_RIGHT){
            View view= LayoutInflater.from(mContex).inflate(R.layout.chat_item_right,parent,false);
            return new AdaptadorChat.ViewHolder(view);
        }else {
            View view= LayoutInflater.from(mContex).inflate(R.layout.chat_item_left,parent,false);
            return new AdaptadorChat.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorChat.ViewHolder holder, int position) {
        Chat chat=mChat.get(position);
        holder.show_message.setText(chat.getMensaje());

        if(imageurl.equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(mContex).load(imageurl).into(holder.profile_image);
        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;
        public ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message=itemView.findViewById(R.id.show_message);
            profile_image=itemView.findViewById(R.id.profile_image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser= FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getEmisor().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}