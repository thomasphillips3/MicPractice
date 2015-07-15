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
import java.io.File;

import android.util.Log;

public class MainActivity extends Activity {

    Button btnRecord, btnSend, btnPlayback;
    private MediaRecorder myAudioRecorder;
    int num = 0;
    private String recordedPhrase = "recording_" + num + ".3gp";
    private File recording = new File(Environment.getExternalStorageDirectory(), recordedPhrase);
    


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

        // Run this method when Record button is pressed down
        btnRecord.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent recordBtnPressed) {

                // Check to see if button is pressed
                switch(recordBtnPressed.getAction() & MotionEvent.ACTION_MASK){
                    // When button is held down
                    case MotionEvent.ACTION_DOWN:
//                        recordedPhrase = null;
                        btnRecord.setPressed(true);
                        Log.v("TOUCH EVENT", "button pressed");
                        startRecording();
                        break;

                    // When button is released
                    case MotionEvent.ACTION_UP:
                        stopRecording();
                        btnRecord.setPressed(false);
                        Log.v("TOUCH EVENT", "button released");
                        break;
                }
                return true;
            }
        });
    }

    private void startRecording() {

//        File recording = new File(Environment.getExternalStorageDirectory(), recordedPhrase);

        // Set various audio parameters
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);


        Log.i("METHOD", "Recording exists. File is called: " + recordedPhrase);
        recordedPhrase = "audioRecording_" + num + ".3gp";
//            recording = new File(Environment.getExternalStorageDirectory(), recordedPhrase);
        myAudioRecorder.setOutputFile(recordedPhrase);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

        // Record audio while button is held
        try {
            // Start recording
            myAudioRecorder.prepare();
            myAudioRecorder.start();
            Log.i("METHOD", "Audio recorder prepared");
        } catch (IOException e){
            e.printStackTrace();
        }
        Toast.makeText(getApplication(), "Audio recording", Toast.LENGTH_LONG).show();
    }

    private void stopRecording() {
        num++;
        try {
            Log.i("METHOD", "Entered 'stopRecording' block");
            myAudioRecorder.stop();
            myAudioRecorder.release();
            myAudioRecorder = null;

        } catch (IllegalStateException e){
            e.printStackTrace();
        }

        Toast.makeText(getApplication(), "Recording finished", Toast.LENGTH_LONG).show();
        Log.v("TOUCH EVENT", btnRecord.toString());
        Log.v("FILE EVENT", "File saved to: " + Environment.getExternalStorageDirectory() + "/" + recordedPhrase);

    }
}

