package com.aspire.tpphilli.micpractice;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import android.os.Bundle;
import android.os.Environment;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


import android.widget.ImageView;
import android.widget.Toast;
import java.io.IOException;

import android.util.Log;

public class MainActivity extends Activity {

    Button btnRecord, btnSend, btnPlayback;
    private MediaRecorder myAudioRecorder;
    private String recordedPhrase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Playback, Record, and Send buttons
        btnPlayback = (Button) findViewById(R.id.btnPlayback);
        btnRecord = (Button) findViewById(R.id.btnRecord);
        btnSend = (Button) findViewById(R.id.btnSend);

        // Since there's nothing to play back or send when the app launches, gray the buttons out
        btnPlayback.setEnabled(false);
        btnSend.setEnabled(false);

        // Set the file name and path to output
        recordedPhrase = Environment.getExternalStorageDirectory().getAbsolutePath() + "/idgaf.mp4";
        ;

        // Set various audio parameters
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.MPEG_4);
        myAudioRecorder.setOutputFile(recordedPhrase);

        btnRecord.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent recordBtnPressed) {
                // Check to see if button is pressed
                switch(recordBtnPressed.getAction() & MotionEvent.ACTION_MASK){
                    // When button is held down
                    case MotionEvent.ACTION_DOWN:
                        btnRecord.setPressed(true);
                        // Record audio while button is held
                        try {
                            myAudioRecorder.prepare();
                            myAudioRecorder.start();
                            Toast.makeText(getApplication(), "Audio recording", Toast.LENGTH_LONG).show();
                            Log.v("TOUCH EVENT", recordBtnPressed.toString());
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                        break;

                    // When button is released
                    case MotionEvent.ACTION_UP:
                        btnRecord.setPressed(false);
                        try {
                            myAudioRecorder.stop();
                            myAudioRecorder.release();
                            Toast.makeText(getApplication(), "Recording finished", Toast.LENGTH_LONG).show();
                            Log.v("TOUCH EVENT", recordBtnPressed.toString());
                            Log.v("FILE EVENT", "File saved to: " + recordedPhrase.toString());
                        } catch (IllegalStateException e2){
                            e2.printStackTrace();
                        }
                        break;
                }

                return true;
            }
        });
    }
}

