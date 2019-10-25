package sv.edu.catolica.bolanios.jonathan.descubreelsalvador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PrincipalElSalvador extends AppCompatActivity {
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final int PERMISSIONS_REQUEST_ENABLE_GPS = 9002;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 9003;
    private boolean mLocationPermissionGranted=false;
    private static final String TAG = "MainActivity";

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
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        logueoFace.setReadPermissions("email");
        LoginManager.getInstance().registerCallback(callM, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                startActivity(new Intent(PrincipalElSalvador.this,AgregarPublicacion.class));
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
 logueoFace.registerCallback(callM, new FacebookCallback<LoginResult>() {
    @Override
    public void onSuccess(LoginResult loginResult) {
        startActivity(new Intent(PrincipalElSalvador.this,AgregarPublicacion.class));
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }
});

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

    private boolean checkMapServices(){
        if(isServicesOK()){
            if(isMapsEnabled()){
                return true;
            }
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Está aplicación necesita permisos de GPS, ¿Quieres habilitarlos?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            // getChatrooms();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(PrincipalElSalvador.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(PrincipalElSalvador.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                          @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callM.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if(!mLocationPermissionGranted){
                    getLocationPermission();
                }
            }
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(checkMapServices()){
            if(!mLocationPermissionGranted){
                //  getChatrooms();
                getLocationPermission();
            }
        }
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
