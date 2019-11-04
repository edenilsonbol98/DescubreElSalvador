package sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Publicaciones;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.R;

public class MyAdapterPublicaciones extends RecyclerView.Adapter<MyAdapterPublicaciones.MyViewHolder> {
    private Context context;
    private ArrayList<ModeloPublicacion> modelos;


    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView textTitulo, textDescripcion,textIdPublicacion,
                        textDepartamento, textTipoLocal;
        public ImageView img;
        public Button btnVermas;
        public MyViewHolder(View v) {
            super(v);
            textTitulo = v.findViewById(R.id.txtTituloList);
            textDescripcion = v.findViewById(R.id.txtDescripcion);
            textDepartamento = v.findViewById(R.id.txtDepartamentoList);
            textIdPublicacion = v.findViewById(R.id.txtIdPubtList);
            textTipoLocal = v.findViewById(R.id.txtTipoLocalList);
            img=v.findViewById(R.id.imgPublicacionList);
            btnVermas= v.findViewById(R.id.btnVermasList);
        }
    }
    public MyAdapterPublicaciones(Context context, ArrayList<ModeloPublicacion> modelos) {
        this.context=context;
        this.modelos=modelos;
    }

    @Override
    public MyAdapterPublicaciones.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_view_publicaciones,null);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
                final ModeloPublicacion modelo = modelos.get(position);
                holder.textTitulo.setText(modelo.getTitulo());
                holder.textDescripcion.setText(modelo.getDescripcion());
                holder.textTipoLocal.setText(modelo.getTipoLocal());
                holder.textIdPublicacion.setText(modelo.getIdPublicacion());
                holder.textDepartamento.setText(modelo.getDepartamento());
                Glide.with(context).load(modelo.getFotos()).into(holder.img);
                holder.btnVermas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getId()== R.id.btnVermasList) {
                            Intent intent = new Intent(context, Publicaciones.class);
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