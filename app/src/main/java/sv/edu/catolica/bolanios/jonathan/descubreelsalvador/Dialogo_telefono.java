package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Dialogo_telefono extends AppCompatActivity {
    EditText telCel;
     EditText telFijo;

    public Dialogo_telefono(final Context contexto){

    public Dialogo_telefono(Context contexto){
        final Dialog dialogo = new Dialog(contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        //dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.cuadro_dialogo);

        telFijo = (EditText) dialogo.findViewById(R.id.txtTelFijo);
        telCel = (EditText) dialogo.findViewById(R.id.txtCelular);
        Button btnagregar = (Button) dialogo.findViewById(R.id.btnAgregarTel);
        telFijo.setInputType(InputType.TYPE_CLASS_NUMBER);
        telCel.setInputType(InputType.TYPE_CLASS_NUMBER);


        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contexto, AgregarPublicacion.class);
                Bundle data = null;
                intent.putExtra("telefono", telCel.getText());
                intent.putExtra("fijo", telCel.getText());
                setResult(RESULT_OK,intent);
                Toast.makeText(contexto, "Pasando valores", Toast.LENGTH_SHORT).show();
                dialogo.dismiss();

            }
        });
        dialogo.show();
    }
}
