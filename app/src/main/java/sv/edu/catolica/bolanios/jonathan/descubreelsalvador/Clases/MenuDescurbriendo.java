package sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Clases;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;

import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.AgregarPublicacion;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Perfil;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.Publicaciones;
import sv.edu.catolica.bolanios.jonathan.descubreelsalvador.R;

public class MenuDescurbriendo extends AppCompatActivity {
    private Context mContext;
    private boolean init = false;
    private Activity activity;

    public MenuDescurbriendo(Activity activity) {
        this.activity = activity;
    }
    public void instanciar(BoomMenuButton btn, Context mContext){
        btn = (BoomMenuButton)findViewById(R.id.boom);

        mContext = this;
    }

    public void crearMenu(BoomMenuButton boomMenuButton){
        // Use a param to record whether the boom button has been initialized
        // Because we don't need to init it again when onResume()
        if (init) return;
        init = true;

        Drawable[] subButtonDrawables = new Drawable[7];
        int[] drawablesResource = new int[]{
                R.drawable.agregar,
                R.drawable.comida,
                R.drawable.hotel,
                R.drawable.chat,
                R.drawable.turi,
                R.drawable.acerca


        };
        for (int i = 0; i < 4; i++)
            subButtonDrawables[i] = ContextCompat.getDrawable(this, drawablesResource[i]);

        String[] subButtonTexts = new String[]{"BoomMenuButton", "View source code", "Follow me", "Otra cosa","Otra cosa"};

        int[][] subButtonColors = new int[3][2];
        for (int i = 0; i < 3; i++) {
            subButtonColors[i][1] = ContextCompat.getColor(this, R.color.azul);
            subButtonColors[i][0] = Util.getInstance().getPressedColor(subButtonColors[i][1]);

        }

        // Now with Builder, you can init BMB more convenient
        new BoomMenuButton.Builder()

                .addSubButton(ContextCompat.getDrawable(this, R.drawable.acerca), subButtonColors[0], "Acerca de nosotros")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.chat), subButtonColors[0], "Chat")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.agregar), subButtonColors[0], "Agregar")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.comida), subButtonColors[0], "Restaurantes")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.turi), subButtonColors[0], "Turicentros")
                .addSubButton(ContextCompat.getDrawable(this, R.drawable.hotel), subButtonColors[0], "Hoteles")


                .button(ButtonType.CIRCLE)
                .boom(BoomType.HORIZONTAL_THROW_2)
                .place(PlaceType.SHARE_6_6)
                .subButtonTextColor(ContextCompat.getColor(this, R.color.Blanco))
                .subButtonsShadow(Util.getInstance().dp2px(1), Util.getInstance().dp2px(1))
                .onSubButtonClick(new BoomMenuButton.OnSubButtonClickListener() {
                    @Override
                    public void onClick(int buttonIndex) {
                        if (buttonIndex == 0) {
                            Intent llamar = new Intent(activity, Publicaciones.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 1) {
                            Intent llamar = new Intent(activity,Publicaciones.class);
                            startActivity(llamar);
                            finish();
                        } else if (buttonIndex == 2) {
                            Intent llamar = new Intent(activity,AgregarPublicacion.class);
                            startActivity(llamar);
                            finish();
                        }else if (buttonIndex == 3) {
                            Intent llamar = new Intent(activity,Publicaciones.class);
                            startActivity(llamar);
                            finish();
                        }else if (buttonIndex == 4) {
                            Intent llamar = new Intent(activity,Publicaciones.class);
                            startActivity(llamar);
                            finish();
                        }else if (buttonIndex == 5) {
                            Intent llamar = new Intent(activity,Publicaciones.class);
                            startActivity(llamar);
                            finish();
                        }else if (buttonIndex == 6) {
                            Intent llamar = new Intent(activity, Perfil.class);
                            startActivity(llamar);
                            finish();
                        }

                    }
                })
                .init(boomMenuButton);
    }
}
