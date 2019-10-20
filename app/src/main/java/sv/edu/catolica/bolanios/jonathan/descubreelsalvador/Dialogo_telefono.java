package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class Dialogo_telefono {

    public Dialogo_telefono(Context contexto){
        final Dialog dialogo = new Dialog(contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        //dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.cuadro_dialogo);

        final EditText telFijo = (EditText) dialogo.findViewById(R.id.txtTelFijo);
        EditText telCel = (EditText) dialogo.findViewById(R.id.txtCelular);
        Button btnagregar = (Button) dialogo.findViewById(R.id.btnAgregarTel);
        telFijo.setInputType(InputType.TYPE_CLASS_NUMBER);

        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.dismiss();
            }
        });
        dialogo.show();
    }
}
