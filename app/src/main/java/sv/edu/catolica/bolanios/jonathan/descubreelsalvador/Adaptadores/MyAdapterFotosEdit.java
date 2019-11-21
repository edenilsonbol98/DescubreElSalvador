package sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Editar_Publicacion;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Fotos_Pantalla_Completa;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.R;


public class MyAdapterFotosEdit extends RecyclerView.Adapter<MyAdapterFotosEdit.MyViewHolder> {
    private Context context;
    private ArrayList<String> fotos;
    public static boolean trigger = false;
    private Editar_Publicacion classEdi;
    public MyAdapterFotosEdit(Context context, ArrayList<String> fotos) {
        this.context = context;
        this.fotos = fotos;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        public ImageButton img1;
        public MyViewHolder(View v) {
            super(v);
            img1 = v.findViewById(R.id.imagenEdit);
        }
    }
    @Override
    public MyAdapterFotosEdit.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lista_fotos_edit,null);
        MyAdapterFotosEdit.MyViewHolder vh = new MyAdapterFotosEdit.MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String foto = fotos.get(position);
        Glide.with(context).load(foto).into(holder.img1);
        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Fotos_Pantalla_Completa.class);
                intent.putExtra("listFotos", fotos);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return 4;
    }
}
