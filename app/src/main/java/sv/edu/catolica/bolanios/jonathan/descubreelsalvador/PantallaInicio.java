package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.security.Principal;

public class PantallaInicio extends AppCompatActivity {
    public Boolean botonBackPresionado = false;
private ImageView imagen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);
        imagen = (ImageView) findViewById(R.id.imagen_inicio);
        Animation animacion = AnimationUtils.loadAnimation(this,R.anim.scale);
        imagen.startAnimation(animacion);
        Handler manejador = new Handler();
        manejador.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                if (!botonBackPresionado) {
                    Intent intencion2= new Intent(PantallaInicio.this, PrincipalElSalvador.class);
                    startActivity(intencion2);
                }

            }
        }, 3000);
    }

    @Override
    public void onBackPressed() {
        botonBackPresionado=true;
        super.onBackPressed();
    }




    }

