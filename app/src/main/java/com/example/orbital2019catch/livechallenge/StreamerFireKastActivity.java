package com.example.orbital2019catch.livechallenge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.orbital2019catch.R;

import io.firekast.FKCamera;
import io.firekast.FKCameraFragment;
import io.firekast.FKError;
import io.firekast.FKPlayerView;
import io.firekast.FKStream;
import io.firekast.FKStreamer;

public class StreamerFireKastActivity extends AppCompatActivity {
    private FKCamera mCamera;
    private FKStreamer mStreamer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streamer_firekast);
        FKCameraFragment cameraFragment = new FKCameraFragment();
        // cameraFragment.getCameraAsync(this);
        //getSupportFragmentManager().beginTransaction()
          //      .replace(R.id.camera_container, cameraFragment)
            //    .commit();


    }

    public void onCameraReady(@Nullable FKCamera camera, @Nullable FKStreamer streamer, @Nullable FKError error) {
        if (error != null) return; // Something went wrong, like device has no camera for example.
        mCamera = camera;
        mStreamer = streamer;
    }


    public void onPause() {
        super.onPause();
        // to disable animation when back button is clicked
        overridePendingTransition(0, 0);
    }

}
