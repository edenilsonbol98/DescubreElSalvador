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

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases.VariablesCompartidas;

public class Dialogo_telefono extends AppCompatActivity {
    EditText telCel;
    EditText telFijo;
    VariablesCompartidas clasVar = new VariablesCompartidas();

    public Dialogo_telefono(final Context contexto) {
            final Dialog dialogo = new Dialog(contexto);
            dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogo.setCancelable(false);
            dialogo.setContentView(R.layout.cuadro_dialogo);

            telFijo =  dialogo.findViewById(R.id.txtTelFijo);
            telCel = dialogo.findViewById(R.id.txtCelular);
            Button btnagregar = dialogo.findViewById(R.id.btnAgregarTel);
            telFijo.setInputType(InputType.TYPE_CLASS_NUMBER);
            telCel.setInputType(InputType.TYPE_CLASS_NUMBER);
        if (Editar_Publicacion.cel!=null && Editar_Publicacion.fijo!=null) {
            telCel.setText(Editar_Publicacion.cel);
            telFijo.setText(Editar_Publicacion.fijo);
        }

            btnagregar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String telefonoX =  telCel.getText().toString();
                    String fijoX = telFijo.getText().toString();
                   // if (!(telefonoX.getText().toString().isEmpty()&& fijoX.getText().toString().isEmpty())) {
                    VariablesCompartidas.setTelefono(telefonoX);
                    VariablesCompartidas.setFijo(fijoX);
                    dialogo.dismiss();
                    //}

                }
            });
            dialogo.show();
        }

}

