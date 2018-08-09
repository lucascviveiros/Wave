package app.waveway.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.koalap.geofirestore.GeoLocation;

public class GPSManager {

    private FusedLocationProviderClient client;
    private LocationRequest locationRequest;
    private Context context;
    private LocationResult locationResult;

    public GPSManager(FusedLocationProviderClient client, LocationRequest locationRequest, Context context) {
        this.client = client;
        this.locationRequest = locationRequest;
        this.context = context;
    }

    public GPSManager() {
    }

    public GPSManager(Context context) {
        this.context = context;
    }

    public void ClientLocationRequest() {
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        int permission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {

            client.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {

                    Location location = locationResult.getLastLocation();

                }
            }, null);
        }
    }
}
