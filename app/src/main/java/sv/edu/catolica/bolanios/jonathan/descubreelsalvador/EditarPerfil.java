package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class EditarPerfil extends AppCompatActivity {
    private Spinner departamento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        departamento=findViewById(R.id.spDepartamento);

        String [] opcionesDepartamento={"Ahuachapán","Sonsonate","Santa Ana","San Salvador","Cuscatlán","Cabañas","Chalatenango","La Libertad","La Paz","San Vicente","Morazán","Usulután","San Miguel","La Unión"};

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,opcionesDepartamento);
        departamento.setAdapter(adaptador);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar, menu);
        return true;
    }
}
