package app.waveway;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.koalap.geofirestore.GeoFire;
import com.koalap.geofirestore.GeoLocation;
import Model.User;
import android.Manifest;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private String phoneNumber;
    private EditText editText_Name;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore firestoreDB;
    private GeoLocation geoLocation;
    String m_provider = LocationManager.NETWORK_PROVIDER;

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private static final String TAG = MainActivity.class.getSimpleName();
    private FusedLocationProviderClient mFusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firestoreDB = FirebaseFirestore.getInstance();


        if (mUser != null) {
            Log.e("usuario", mUser.getPhoneNumber());
        } else {
            Log.e("error", "deu ruim");
        }
        editText_Name = findViewById(R.id.id_textNome);

        findViewById(R.id.id_button_salvar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameuser = editText_Name.getText().toString();
                CreateUser(nameuser);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!checkPermissions()) {
            startLocationPermissionRequest();
        } else {
        }
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(RegisterActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    public void checkEditText() {
        String name = editText_Name.getText().toString();
        if (name.isEmpty()) {
            editText_Name.setError("Digite o nome!");
            editText_Name.requestFocus();
            return;
        }
    }

    public void CreateUser(String name) {

        LocationRequest request = new LocationRequest();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission == PackageManager.PERMISSION_GRANTED) {

            client.requestLocationUpdates(request, new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {

                            CollectionReference ref = FirebaseFirestore.getInstance().collection("local");
                            GeoFire geoFire = new GeoFire(ref);

                            Location location = locationResult.getLastLocation();
                            if (location != null) {

                                Log.e("location", String.valueOf(location));

                                GeoLocation geoLocation = new GeoLocation(location.getLatitude(), location.getLongitude());
                                geoFire.setLocation("firebase-hq", geoLocation,
                                        new GeoFire.CompletionListener() {
                                            @Override
                                            public void onComplete(String key, Exception exception) {
                                                if (exception != null) {
                                                    System.err.println("There was an error saving the location to GeoFire: " + exception.toString());
                                                } else {
                                                    System.out.println("Location saved on server successfully!");
                                                }
                                            }
                                        });

                                User user = new User(phoneNumber, name, geoLocation);

                                firestoreDB.collection("User").document(mUser.getUid()).set(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(RegisterActivity.this, "SHOW!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                            }
                        }
                            }, null);

        }

    }
}


/*        Bundle extra = getIntent().getExtras();
        if(extra != null) {
            phoneNumber = extra.getString("idPhoneNumber");
        }*/