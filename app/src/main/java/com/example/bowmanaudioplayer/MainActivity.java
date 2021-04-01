package com.example.bowmanaudioplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;

public class MainActivity extends AppCompatActivity {

    Button pickButton;
    Button playButton;
    TextView filePathTextView;
    Uri audioFile;

    SimpleExoPlayer player;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        filePathTextView = findViewById ( R.id.fileid );
        pickButton = findViewById ( R.id.selectbutton );
        playButton = findViewById ( R.id.playbutton );
//        playButton.setClickable ( false );

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
        playButton.setClickable ( false );
        if( ContextCompat.checkSelfPermission ( this, Manifest.permission.READ_EXTERNAL_STORAGE )== PackageManager.PERMISSION_DENIED ) {
            ActivityCompat.requestPermissions (
                    this ,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} , 200 );
        }
    }

    void startPlaying(){
        player = new SimpleExoPlayer.Builder (this).build();
        MediaItem mediaItem = MediaItem.fromUri ( audioFile );
        player.setMediaItem ( mediaItem );
        player.setPlayWhenReady ( true );
        player.prepare ();
    }
    void stopPlaying(){
        player.release ();
        player = null;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult ( requestCode, resultCode, data );
        if(resultCode == RESULT_OK && requestCode == 1){
            audioFile = data.getData ( );
            filePathTextView.setText(audioFile.getPath());
            playButton.setClickable ( true );
        }else {
            filePathTextView.setText ( "No file selected" );
            playButton.setClickable(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode , @NonNull String[] permissions , @NonNull int[] grantResults) {
        super.onRequestPermissionsResult ( requestCode , permissions , grantResults );
        if(requestCode == 200){
            if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_DENIED){
                Toast.makeText ( this , "Permission Granted" , Toast.LENGTH_SHORT ).show ( );

                playButton.setClickable ( true );
                pickButton.setClickable ( true );
            }else{
                Toast.makeText ( this , "Permission Denied" , Toast.LENGTH_SHORT ).show ( );
                playButton.setClickable ( false );
                pickButton.setClickable ( false );
            }
        }
    }
}