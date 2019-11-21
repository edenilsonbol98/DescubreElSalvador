package sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Dialogos.DialogoSimpleFotos;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.EditarPerfil;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Editar_Publicacion;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Perfil;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Publicaciones;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.R;

public class MyAdapterEditarPublicaciones extends RecyclerView.Adapter<MyAdapterEditarPublicaciones.MyViewHolder> {
    private Context context;
    private ArrayList<ModeloPublicacion> modelos;
    private FirebaseFirestore myRef;


    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView textTitulo, textDescripcion,textIdPublicacion;
        public ImageView img;
        public ImageView btnEditar;
        public ImageView btnEliminar;
        public MyViewHolder(View v) {
            super(v);
            textTitulo = v.findViewById(R.id.txtTituloEdit);
            textDescripcion = v.findViewById(R.id.txtDescripcionEdit);
            textIdPublicacion = v.findViewById(R.id.txtIdPubEditar);
            img=v.findViewById(R.id.imgPublicacionEdit);
            btnEditar= v.findViewById(R.id.btnPublicacionEdit);
            btnEliminar= v.findViewById(R.id.btneliminar);
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
                holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (v.getId()== R.id.btneliminar) {
                            String idPubEl =holder.textIdPublicacion.getText().toString();
                            dialogo(idPubEl);
                        }
                    }
                });
            }
    @Override
    public int getItemCount() {
        return modelos.size();
    }

    private void  dialogo(final String idEl){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Eliminar publicación");
        alertDialogBuilder
                .setMessage("¿Esta seguro de eliminar la publicación?")
                .setCancelable(false)
                .setPositiveButton("Si",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        myRef= FirebaseFirestore.getInstance();
                        myRef.collection("publicacion").document(idEl).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                System.exit(0);
                                Intent intent = new Intent(context, Perfil.class);
                                context.startActivities(new Intent[]{intent});
                            }
                        });
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                }).create().show();
    }
}