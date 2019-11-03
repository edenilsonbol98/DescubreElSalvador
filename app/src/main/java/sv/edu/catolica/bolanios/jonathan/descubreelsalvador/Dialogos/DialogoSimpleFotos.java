package sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.AgregarPublicacion;


public class DialogoSimpleFotos extends DialogFragment {

    public DialogoSimpleFotos() {
    }
    public static boolean entro=false;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return onCreateDialog();
    }

    public Dialog onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Solo puede eligir 4 imagenes, ¿Desea volver a eligir las imagenes?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                       entro = true;
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        try {
                            this.finalize();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
