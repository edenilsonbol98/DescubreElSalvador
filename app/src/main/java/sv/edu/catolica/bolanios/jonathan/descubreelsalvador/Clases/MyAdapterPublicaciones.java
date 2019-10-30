package sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import ahmed.easyslider.EasySlider;
import ahmed.easyslider.SliderItem;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.MapaPublicacion;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.R;

public class MyAdapterPublicaciones extends RecyclerView.Adapter<MyAdapterPublicaciones.MyViewHolder> {
    private Context context;
    private ArrayList<ModeloPublicacion> modelos;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textTitulo, textDescripcion,textIdPublicacion,
                        textDepartamento, textTipoLocal;
        public EasySlider easySlider;
        public MyViewHolder(View v) {
            super(v);
            textTitulo = v.findViewById(R.id.txtTituloList);
            textDescripcion = v.findViewById(R.id.txtDescripcion);
            textDepartamento = v.findViewById(R.id.txtDepartamentoList);
            textIdPublicacion = v.findViewById(R.id.txtIdPubtList);
            textTipoLocal = v.findViewById(R.id.txtTipoLocalList);
            easySlider=v.findViewById(R.id.sliderList);
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
        ModeloPublicacion modelo = modelos.get(position);
        holder.textTitulo.setText(modelo.getTitulo());
        holder.textDescripcion.setText(modelo.getDescripcion());
        holder.textTipoLocal.setText(modelo.getTipoLocal());
        holder.textIdPublicacion.setText(modelo.getIdPublicacion());
        holder.textDepartamento.setText(modelo.getDepartamento());
        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem("Foto 1",modelo.getFotos().get(0)));
        sliderItems.add(new SliderItem("Foto 2",modelo.getFotos().get(1)));
        sliderItems.add(new SliderItem("Foto 3",modelo.getFotos().get(2)));
        sliderItems.add(new SliderItem("Foto 4",modelo.getFotos().get(3)));
        holder.easySlider.setPages(sliderItems);
            }
    @Override
    public int getItemCount() {
        return modelos.size();
    }
}