package com.example.orbital2019catch.location;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationBuilderWithBuilderAccessor;

import com.example.orbital2019catch.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;

import java.util.Random;

public class LocationBasedActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private static final LatLng pumaBugis = new LatLng(1.2998, 103.8541);
    private static final LatLng soc = new LatLng(1.2951,103.7739);
    private static final LatLng jewel = new LatLng(1.3596, 103.9895);
    private static final LatLng mcdonaldsCauseway = new LatLng(1.4364, 103.7865);
    private static final LatLng ntucJurongPt = new LatLng(1.3398, 103.7071);
    private static final LatLng merlionPark = new LatLng(1.2868, 103.8545);

    private Marker mPumaBugis;
    private Marker mSOC;
    private Marker mJewel;
    private Marker mMCDCausewayPt;
    private Marker mNTUCJurongPt;
    private Marker mMerlion;

    private static final int MY_PERMISSION_REQUEST_CODE = 31297;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 270399;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private static int UPDATE_INTERVAL = 5000; // 5seconds
    private static int FASTEST_INTERVAL = 3000;
    private static int DISPLACEMENT = 10;

    private static int FENCE_RADIUS = 500;

    DatabaseReference ref;
    GeoFire geoFire;
    Marker currLocation;

    VerticalSeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_based);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ref = FirebaseDatabase.getInstance().getReference("MyLocation");
        geoFire = new GeoFire(ref);

        mSeekBar = (VerticalSeekBar) findViewById(R.id.verticalSeekBar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mMap.animateCamera(CameraUpdateFactory.zoomTo(progress), 2000, null);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        setUpLocation();
    }

    private void setUpLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request runtime permission
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_REQUEST_CODE);
        } else {
            if (checkPlayServices()) {
                buildGoogleApiClient();
                createLocationRequest();
                displayLocation();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @androidx.annotation.NonNull String[] permissions, @androidx.annotation.NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkPlayServices()) {
                        buildGoogleApiClient();
                        createLocationRequest();
                        displayLocation();
                    }
                }
                break;
        }
    }

    private void displayLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationServices.getFusedLocationProviderClient(this)
                .getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Task completed successfully
                            mLastLocation = task.getResult();
                            final double latitude = mLastLocation.getLatitude();
                            final double longitude = mLastLocation.getLongitude();

                            // Update to Firebase
                            geoFire.setLocation(FirebaseAuth.getInstance().getUid(),
                                    new GeoLocation(latitude, longitude), new GeoFire.CompletionListener() {
                                        @Override
                                        public void onComplete(String key, DatabaseError error) {
                                            // Add marker
                                            if (currLocation != null) {
                                                currLocation.remove(); // remove old marker
                                            }
                                            currLocation = mMap.addMarker(new MarkerOptions()
                                                    .position(new LatLng(latitude, longitude))
                                                    .title("Your current location")
                                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                                            // move camera to your position
                                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12.0f));
                                        }
                                    });
                                    Log.d("catch location", String.format("Your location was changed: %f / %f", latitude, longitude));
                        } else {
                            // Task failed with an exception
                            Log.d("catch location", "Catch is unable to get your location!");
                        }
                    }
                });
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            Toast.makeText(this, "This device is not supported", Toast.LENGTH_SHORT).show();
            finish();
            return false;
        }
        return true;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );

      //  if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            // Call your Alert message
            Toast.makeText(this, "Consider turning on Location Services for a better experience!", Toast.LENGTH_SHORT).show();
            CameraUpdate singapore = CameraUpdateFactory.newLatLng(new LatLng(1.35, 103.81));
            // moves camera to coordinates
            mMap.moveCamera(singapore);
      /*  } else {
            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            CameraUpdate currLocation = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
            mMap.moveCamera(currLocation);
        }
        */

        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.5f));
        //  adding markers
        mPumaBugis = mMap.addMarker(new MarkerOptions()
                .position(pumaBugis)
                .title("Puma at Bugis+")
                .snippet("Payout: $0.40"));
        addFence(pumaBugis);
        //        .icon(BitmapDescriptorFactory.fromResource(R.drawable.puma)));
        mPumaBugis.setTag(0);
        mSOC = mMap.addMarker(new MarkerOptions()
                .position(soc)
                .title("NUS School of Computing")
                .snippet("Payout: $0.20"));
         //       .icon(BitmapDescriptorFactory.fromResource(R.drawable.soc)));
        addFence(soc);
        mSOC.setTag(0);
        mJewel = mMap.addMarker(new MarkerOptions()
                .position(jewel)
                .title("Jewel")
                .snippet("Payout: $0.60"));
         //       .icon(BitmapDescriptorFactory.fromResource(R.drawable.jewel)));
        addFence(jewel);
        mJewel.setTag(0);
        mMCDCausewayPt = mMap.addMarker(new MarkerOptions()
                .position(mcdonaldsCauseway)
                .title("McDonald's at Causeway Point")
                .snippet("Payout: $0.30"));
         //       .icon(BitmapDescriptorFactory.fromResource(R.drawable.mcd)));
        addFence(mcdonaldsCauseway);
        mMCDCausewayPt.setTag(0);
        mNTUCJurongPt = mMap.addMarker(new MarkerOptions()
                .position(ntucJurongPt)
                .title("FairPrice at Jurong Point")
                .snippet("Payout: $0.50"));
         //       .icon(BitmapDescriptorFactory.fromResource(R.drawable.ntuc)));
        addFence(ntucJurongPt);
        mNTUCJurongPt.setTag(0);
        /*
        mMerlion = mMap.addMarker(new MarkerOptions()
                .position(merlionPark)
                .title("Merlion Park")
                .snippet("Payout: $0.70"));
        //        .icon(BitmapDescriptorFactory.fromResource(R.drawable.merlion)));
        addFence(merlionPark);
        mMerlion.setTag(0);
        */
        //  Set a listener for marker click
        // mMap.setOnMarkerClickListener(this);

        // Set listener on the snippet window
        mMap.setOnInfoWindowClickListener(this);
    }

    private void addFence(LatLng location) {
        mMap.addCircle(new CircleOptions()
                .center(location)
                .radius(500) // in metres
                .strokeColor(Color.BLUE)
                .fillColor(0x220000FF)
                .strokeWidth(5.0f)
        );

        // Add GeoQuery here
        // 0.5f = 0.5km
        GeoQuery geoQuery = geoFire.queryAtLocation(
                new GeoLocation(location.latitude, location.longitude), 0.5f);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                sendNotification("Catch", String.format("Check Catch now to check what location-based surveys you can do!"));
            }

            @Override
            public void onKeyExited(String key) {
                sendNotification("Catch", String.format("Check Catch to see what surveys you could have done!"));
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                Log.d("MOVE", String.format("User moved within the geofence at [%f, %f]", location.latitude, location.longitude));
            }

            @Override
            public void onGeoQueryReady() {
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                Log.e("ERROR", ""+error);
            }
        });
    }

    private void sendNotification(String title, String content) {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.drawable.catch_logo)
                .setContentTitle(title)
                .setContentText(content);
        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, LocationBasedActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(contentIntent);
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.defaults = Notification.DEFAULT_SOUND;
        manager.notify(new Random().nextInt(), notification);
    }

    public void onPause() {
        super.onPause();
        // to disable animation when back button is clicked
        overridePendingTransition(0, 0);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (marker.equals(mJewel) && distance(currLocation.getPosition(), mJewel.getPosition()) <  FENCE_RADIUS) {
            Intent intent = new Intent(this, JewelFeedbackActivity.class);
            startActivity(intent);
        } else if (marker.equals(mSOC) && distance(currLocation.getPosition(), mSOC.getPosition()) < FENCE_RADIUS) {
            Intent intent = new Intent(this, SOCFeedbackActivity.class);
            startActivity(intent);
        } else if (marker.equals(mMCDCausewayPt) && distance(currLocation.getPosition(), mMCDCausewayPt.getPosition()) < FENCE_RADIUS) {
            Intent intent = new Intent(this, SurveyFirebaseMCD.class);
            startActivity(intent);
        } else if (marker.equals(mMerlion) && distance(currLocation.getPosition(), mMerlion.getPosition()) < FENCE_RADIUS) {
            Intent intent = new Intent(this, SurveyFirebaseMerlion.class);
            startActivity(intent);
        } else if (marker.equals(mNTUCJurongPt) && distance(currLocation.getPosition(), mNTUCJurongPt.getPosition()) < FENCE_RADIUS) {
            Intent intent = new Intent(this, SurveyFirebaseNTUC.class);
            startActivity(intent);
        } else if (marker.equals(mPumaBugis) && distance(currLocation.getPosition(), mPumaBugis.getPosition()) < FENCE_RADIUS)  {
            Intent intent = new Intent(this, SurveyFirebasePuma.class);
            startActivity(intent);
        }
    }

    public float distance (LatLng x, LatLng y) {
        float lat_x = (float) x.latitude;
        float lng_x = (float) x.longitude;
        float lat_y = (float) y.latitude;
        float lng_y = (float) y.longitude;
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_y-lat_x);
        double lngDiff = Math.toRadians(lng_y-lng_x);
        double a = Math.sin(latDiff /2) * Math.sin(latDiff /2) +
                Math.cos(Math.toRadians(lat_x)) * Math.cos(Math.toRadians(lat_y)) *
                        Math.sin(lngDiff /2) * Math.sin(lngDiff /2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = earthRadius * c;

        int meterConversion = 1609;

        return new Float(distance * meterConversion).floatValue();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        /*
        if (marker.equals(mJewel)) {
            Intent intent = new Intent(this, JewelFeedbackActivity.class);
            startActivity(intent);
        } else if (marker.equals(mSOC)) {
            Intent intent = new Intent(this, SOCFeedbackActivity.class);
            startActivity(intent);
        } else if (marker.equals(mMCDCausewayPt)) {
            Intent intent = new Intent(this, SurveyFirebaseMCD.class);
            startActivity(intent);
        } else if (marker.equals(mMerlion)) {
            Intent intent = new Intent(this, SurveyFirebaseMerlion.class);
            startActivity(intent);
        } else if (marker.equals(mNTUCJurongPt)) {
            Intent intent = new Intent(this, SurveyFirebaseNTUC.class);
            startActivity(intent);
        } else if (marker.equals(mPumaBugis)) {
            Intent intent = new Intent(this, SurveyFirebasePuma.class);
            startActivity(intent);
        }
        */
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        displayLocation();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        displayLocation();
        startLocationUpdates();
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(mLocationRequest, null);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
