package sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.R;


public class MyAdapterFotos extends RecyclerView.Adapter<MyAdapterFotos.MyViewHolder> {
    private Context context;
    private ArrayList<String> fotos;

    public MyAdapterFotos(Context context, ArrayList<String> fotos) {
        this.context = context;
        this.fotos = fotos;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  {
        public ImageButton img1;
        public MyViewHolder(View v) {
            super(v);
            img1 = v.findViewById(R.id.imagenSlider);
        }
    }
    @Override
    public MyAdapterFotos.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.lista_view_fotos,null);
        MyAdapterFotos.MyViewHolder vh = new MyAdapterFotos.MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String foto = fotos.get(position);
        Glide.with(context).load(foto).into(holder.img1);
        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Item "+1, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
