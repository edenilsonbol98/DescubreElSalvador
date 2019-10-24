package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class PrincipalElSalvador extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private LoginButton logueoFace;
    private CallbackManager callM;
    private AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_el_salvador);

        mAuth = FirebaseAuth.getInstance();
logueoFace=findViewById(R.id.btnLoginFacebook);
callM=CallbackManager.Factory.create();

 accessToken = AccessToken.getCurrentAccessToken();


logueoFace.registerCallback(callM, new FacebookCallback<LoginResult>() {
    @Override
    public void onSuccess(LoginResult loginResult) {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }
});

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callM.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker tokenTracker=new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken==null){

                Toast.makeText(PrincipalElSalvador.this,"Usuario no logueado",Toast.LENGTH_LONG).show();
            }else {
                loguearse(currentAccessToken);
                startActivity(new Intent(PrincipalElSalvador.this,AgregarPublicacion.class));

            }
        }

        private void loguearse(AccessToken nuevoAccessToken){
            final GraphRequest request=GraphRequest.newMeRequest(nuevoAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        String first_name=object.getString("first_name");
                        String last_name=object.getString("last_name");
                        String email=object.getString("email");
                        String id=object.getString("id");




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            Bundle parameters=new Bundle();
            parameters.putString("fields","first_name,last_name,email,id");
            request.setParameters(parameters);
            request.executeAsync();
        }
        public void checkLoginStatus(){
            if(AccessToken.getCurrentAccessToken()!=null){
                loguearse(AccessToken.getCurrentAccessToken());
                startActivity(new Intent(PrincipalElSalvador.this,AgregarPublicacion.class));
            }
        }

    };




    public void irSesion(View view) {
        Intent intencion1 = new Intent(PrincipalElSalvador.this, Login.class);
        startActivity(intencion1);
    }
    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(PrincipalElSalvador.this,AgregarPublicacion.class));
            finish();
        }

    }
}
