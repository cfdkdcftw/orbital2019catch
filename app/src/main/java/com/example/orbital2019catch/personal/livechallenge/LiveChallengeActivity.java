package com.example.orbital2019catch.personal.livechallenge;

import android.media.MediaPlayer;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.orbital2019catch.R;
import com.example.orbital2019catch.personal.profile.UserProfile;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LiveChallengeActivity extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<ChatMessage> adapter;
    private VideoView mainVideoView;
    private Uri videoUri;
    private boolean isPlaying = false;
    private ProgressBar bufferProgress;
    FloatingActionButton sendMessage;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    String displayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_challenge);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = mDatabase.getReference("users/" + mAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                displayName = userProfile.getName();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LiveChallengeActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
        setContentView(R.layout.activity_live_challenge);
        sendMessage = (FloatingActionButton) findViewById(R.id.sendMessage);
        bufferProgress = (ProgressBar) findViewById(R.id.videoProgressBar);
        mainVideoView = (VideoView) findViewById(R.id.mainVideoView);
        videoUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/orbital2019catch.appspot.com/o/milestone3_livestream.mp4?alt=media&token=ddc79e8f-14b1-47d9-b592-83c7724a81f9");
        mainVideoView.setVideoURI(videoUri);
        mainVideoView.requestFocus();
        // to check whether to show buffering image
        mainVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == mp.MEDIA_INFO_BUFFERING_START) {
                    bufferProgress.setVisibility(View.VISIBLE);
                } else if (what == mp.MEDIA_INFO_BUFFERING_END) {
                    bufferProgress.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
        mainVideoView.start();
        isPlaying = true;
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText) findViewById(R.id.chat_input);
                FirebaseDatabase.getInstance().getReference("livechat")
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                displayName));
                input.setText("");
            }
                                       }
        );
        displayChatMessage();
    }

    private void displayChatMessage() {
        ListView listOfMessage = (ListView) findViewById(R.id.text_list_view);
        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,R.layout.list_messages, FirebaseDatabase.getInstance().getReference("livechat")) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // get references to the views of list item.xml
                TextView messageText, messageUser;
                messageText = (TextView) v.findViewById(R.id.message_text);
                messageUser = (TextView) v.findViewById(R.id.message_user);
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
            }
        };
        listOfMessage.setAdapter(adapter);
    }

    public void onPause() {
        super.onPause();
        // to disable animation when back button is clicked
        overridePendingTransition(0, 0);
    }
}
