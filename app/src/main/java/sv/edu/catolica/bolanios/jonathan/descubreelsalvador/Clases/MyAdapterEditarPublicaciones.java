package sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Editar_Publicacion;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Publicaciones;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.R;

public class MyAdapterEditarPublicaciones extends RecyclerView.Adapter<MyAdapterEditarPublicaciones.MyViewHolder> {
    private Context context;
    private ArrayList<ModeloPublicacion> modelos;


    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView textTitulo, textDescripcion,textIdPublicacion;
        public ImageView img;
        public ImageView btnEditar;
        public MyViewHolder(View v) {
            super(v);
            textTitulo = v.findViewById(R.id.txtTituloEdit);
            textDescripcion = v.findViewById(R.id.txtDescripcionEdit);
            textIdPublicacion = v.findViewById(R.id.txtIdPubEditar);
            img=v.findViewById(R.id.imgPublicacionEdit);
            btnEditar= v.findViewById(R.id.btnPublicacionEdit);
        }
    }
    public MyAdapterEditarPublicaciones(Context context, ArrayList<ModeloPublicacion> modelos) {
        this.context=context;
        this.modelos=modelos;
    }

    @Override
    public MyAdapterEditarPublicaciones.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                        int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_editar_publicaciones,null);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
                final ModeloPublicacion modelo = modelos.get(position);
                holder.textTitulo.setText(modelo.getTitulo());
                holder.textDescripcion.setText(modelo.getDescripcion());
                holder.textIdPublicacion.setText(modelo.getIdPublicacion());
                Glide.with(context).load(modelo.getFotos()).into(holder.img);
                holder.btnEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getId()== R.id.btnPublicacionEdit) {
                            Intent intent = new Intent(context, Editar_Publicacion.class);
                            intent.putExtra("idPublicacion",holder.textIdPublicacion.getText().toString());
                            context.startActivities(new Intent[]{intent});
                        }
                    }
                });
            }
    @Override
    public int getItemCount() {
        return modelos.size();
    }
}