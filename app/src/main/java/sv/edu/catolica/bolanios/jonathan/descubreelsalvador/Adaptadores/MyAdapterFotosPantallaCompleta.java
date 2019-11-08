package sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.R;


public class MyAdapterFotosPantallaCompleta extends RecyclerView.Adapter<MyAdapterFotosPantallaCompleta.MyViewHolder> {
    private Context context;
    private ArrayList<String> fotos;

    public MyAdapterFotosPantallaCompleta(Context context, ArrayList<String> fotos) {
        this.context = context;
        this.fotos = fotos;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        public ImageView img2;
        public MyViewHolder(View v) {
            super(v);
            img2 = v.findViewById(R.id.imgFotoPantallaCompleta);
        }
    }
    @Override
    public MyAdapterFotosPantallaCompleta.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_fotos_pantalla_completa,null);
        MyAdapterFotosPantallaCompleta.MyViewHolder vh = new MyAdapterFotosPantallaCompleta.MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String foto = fotos.get(position);
        Glide.with(context).load(foto).into(holder.img2);
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
