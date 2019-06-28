package com.example.orbital2019catch.location;

import android.content.Context;
import android.graphics.Camera;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.orbital2019catch.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationBasedActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_based);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mPumaBugis = mMap.addMarker(new MarkerOptions().position(pumaBugis).title("Puma at Bugis+"));
        mPumaBugis.setTag(0);
        mSOC = mMap.addMarker(new MarkerOptions().position(soc).title("NUS School of Computing"));
        mSOC.setTag(0);
        mJewel = mMap.addMarker(new MarkerOptions().position(jewel).title("Jewel"));
        mJewel.setTag(0);
        mMCDCausewayPt = mMap.addMarker(new MarkerOptions().position(mcdonaldsCauseway).title("McDonald's at Causeway Point"));
        mMCDCausewayPt.setTag(0);
        mNTUCJurongPt = mMap.addMarker(new MarkerOptions().position(ntucJurongPt).title("FairPrice at Jurong Point"));
        mNTUCJurongPt.setTag(0);
        mMerlion = mMap.addMarker(new MarkerOptions().position(merlionPark).title("Merlion Park"));
        mMerlion.setTag(0);

        //  Set a listener for marker click
        mMap.setOnMarkerClickListener(this);
    }

    public void onPause() {
        super.onPause();
        // to disable animation when back button is clicked
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // to start survey/feedback
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
