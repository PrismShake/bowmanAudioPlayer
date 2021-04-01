package com.example.bowmanaudioplayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;

public class MainActivity extends AppCompatActivity {

    Button pickButton;
    Button playButton;
    TextView filePathTextView;
    Uri audioFile;

    SimpleExoPlayer player;
    private boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        filePathTextView = findViewById ( R.id.fileid );
        pickButton = findViewById ( R.id.selectbutton );
        playButton = findViewById ( R.id.playbutton );
        playButton.setClickable ( false );

        pickButton.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View v) {
                Intent pickIntent = new Intent();
                pickIntent.setAction ( Intent.ACTION_GET_CONTENT );
                pickIntent.setType ( "audio/*" );
                startActivityForResult ( pickIntent, 1 );
            }
        });

        playButton.setOnClickListener ( new View.OnClickListener ( ) {


            @Override
            public void onClick(View v) {
                if(isPlaying){

                    stopPlaying ();
                    playButton.setText("Start Playing");
                }else{
                    startPlaying ();
                    playButton.setText("Stop Playing");
                }
                isPlaying = !isPlaying;
            }
        } );

    }
    void startPlaying(){

    }
    void stopPlaying(){

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult ( requestCode, resultCode, data );
        if(resultCode == RESULT_OK && requestCode == 1){
            audioFile = data.getData ( );
            filePathTextView.setText(audioFile.getPath());
        }else {
            filePathTextView.setText ( "No file selected" );
            playButton.setClickable(false);
        }
    }

}