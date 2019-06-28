package com.example.orbital2019catch.location;

import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.orbital2019catch.MainActivity;
import com.example.orbital2019catch.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationBasedActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener{

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
        mPumaBugis = mMap.addMarker(new MarkerOptions()
                .position(pumaBugis)
                .title("Puma at Bugis+")
                .snippet("Payout: $0.40"));
        //        .icon(BitmapDescriptorFactory.fromResource(R.drawable.puma)));
        mPumaBugis.setTag(0);
        mSOC = mMap.addMarker(new MarkerOptions()
                .position(soc)
                .title("NUS School of Computing")
                .snippet("Payout: $0.20"));
         //       .icon(BitmapDescriptorFactory.fromResource(R.drawable.soc)));
        mSOC.setTag(0);
        mJewel = mMap.addMarker(new MarkerOptions()
                .position(jewel)
                .title("Jewel")
                .snippet("Payout: $0.60"));
         //       .icon(BitmapDescriptorFactory.fromResource(R.drawable.jewel)));
        mJewel.setTag(0);
        mMCDCausewayPt = mMap.addMarker(new MarkerOptions()
                .position(mcdonaldsCauseway)
                .title("McDonald's at Causeway Point")
                .snippet("Payout: $0.30"));
         //       .icon(BitmapDescriptorFactory.fromResource(R.drawable.mcd)));
        mMCDCausewayPt.setTag(0);
        mNTUCJurongPt = mMap.addMarker(new MarkerOptions()
                .position(ntucJurongPt)
                .title("FairPrice at Jurong Point")
                .snippet("Payout: $0.50"));
         //       .icon(BitmapDescriptorFactory.fromResource(R.drawable.ntuc)));
        mNTUCJurongPt.setTag(0);
        /*
        mMerlion = mMap.addMarker(new MarkerOptions()
                .position(merlionPark)
                .title("Merlion Park")
                .snippet("Payout: $0.70"));
        //        .icon(BitmapDescriptorFactory.fromResource(R.drawable.merlion)));
        mMerlion.setTag(0);
        */
        //  Set a listener for marker click
        // mMap.setOnMarkerClickListener(this);

        // Set listener on the snippet window
        mMap.setOnInfoWindowClickListener(this);
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
}
