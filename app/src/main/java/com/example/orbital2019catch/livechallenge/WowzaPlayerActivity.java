package com.example.orbital2019catch.livechallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.orbital2019catch.R;
import com.wowza.gocoder.sdk.api.configuration.WOWZMediaConfig;
import com.wowza.gocoder.sdk.api.player.WOWZPlayerConfig;
import com.wowza.gocoder.sdk.api.player.WOWZPlayerView;
import com.wowza.gocoder.sdk.api.status.WOWZStatus;
import com.wowza.gocoder.sdk.api.status.WOWZStatusCallback;

public class WowzaPlayerActivity extends AppCompatActivity {

    private WOWZPlayerView mStreamPlayerView;
    private WOWZPlayerConfig mStreamPlayerConfig;
    private String hostAddress = getString(R.string.wowza_host_address);
    private String appName = getString(R.string.wowza_application_name);
    private String streamName = getString(R.string.wowza_stream_name);
    private int portNum = Integer.parseInt(getString(R.string.wowza_port));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wowza_player);
        mStreamPlayerView = (WOWZPlayerView) findViewById(R.id.vwStreamPlayer);
        mStreamPlayerConfig = new WOWZPlayerConfig();
        mStreamPlayerConfig.setIsPlayback(true);
        mStreamPlayerConfig.setHostAddress(hostAddress);
        mStreamPlayerConfig.setApplicationName(appName);
        mStreamPlayerConfig.setStreamName(streamName);
        mStreamPlayerConfig.setPortNumber(portNum);

        mStreamPlayerConfig.setHLSEnabled(true);
        mStreamPlayerConfig.setHLSBackupURL("http://" + hostAddress + ":" + portNum + "/" + appName + "/" + streamName + "/playlist.m3u8");

        mStreamPlayerConfig.setAudioEnabled(true);
        mStreamPlayerConfig.setVideoEnabled(true);

        // WOWZMediaConfig.FILL_VIEW : WOWZMediaConfig.RESIZE_TO_ASPECT;
        mStreamPlayerView.setScaleMode(WOWZMediaConfig.FILL_VIEW);

        WOWZStatusCallback statusCallback = new StatusCallback();
        mStreamPlayerView.play(mStreamPlayerConfig, statusCallback);
    }

    class StatusCallback implements WOWZStatusCallback {
        @Override
        public void onWZStatus(WOWZStatus wzStatus) {
        }
        @Override
        public void onWZError(WOWZStatus wzStatus) {
        }
    }
}
