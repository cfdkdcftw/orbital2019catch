package com.example.orbital2019catch.deprecated;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.orbital2019catch.R;

import io.firekast.FKPlayer;
import io.firekast.FKPlayerView;
import io.firekast.FKStream;

public class PlayerFireKastActivity extends AppCompatActivity {

    private FKPlayerView mPlayerView;
    private FKPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_challenge);
        mPlayerView = (FKPlayerView) findViewById(R.id.playerView);
        mPlayer = mPlayerView.getPlayer();
        FKStream stream = FKStream.newEmptyInstance("STREAM_ID");
        mPlayer.play(stream);

    }

    public void onPause() {
        super.onPause();
        // to disable animation when back button is clicked
        overridePendingTransition(0, 0);
    }
}
