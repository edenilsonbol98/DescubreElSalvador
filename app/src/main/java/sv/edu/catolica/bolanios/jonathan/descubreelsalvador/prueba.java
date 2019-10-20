package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class prueba extends AppCompatActivity {
Button mostrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);

        mostrar = (Button) findViewById(R.id.btnPrueba);
        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(prueba.this);
                View mView = getLayoutInflater().inflate(R.layout.buscar_hospedaje,null);
                mBuilder.setTitle("Buscar hospedajes");
                final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spDepartamentoHos);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(prueba.this,android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.DepartamentosS));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                mBuilder.setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(prueba.this, mSpinner.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
    }
}
