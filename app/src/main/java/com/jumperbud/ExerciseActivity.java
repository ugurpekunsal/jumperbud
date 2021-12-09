package com.jumperbud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jumperbud.R;
import com.jumperbud.motiondetection.motiondetection.MotionDetector;
import com.jumperbud.motiondetection.motiondetection.MotionDetectorCallback;

import java.util.Timer;
import java.util.TimerTask;

public class ExerciseActivity extends AppCompatActivity {
    private TextView txtTime, txtJumps;
    private MotionDetector motionDetector;
    private Button button_purchase, button_start, button_pause, button_finish;
    private ImageButton button_exit;
    private boolean pauseTimer = false;
    private int count = 0, jumps = 0;
    private Timer T;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        button_purchase = (Button) findViewById(R.id.button_purchase);
        button_start = (Button) findViewById(R.id.button_start);
        button_pause = (Button) findViewById(R.id.button_pause);
        button_finish = (Button) findViewById(R.id.button_finish);
        button_exit = (ImageButton) findViewById(R.id.button_exit);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtJumps = (TextView) findViewById(R.id.txtJumps);
        T = new Timer();

        motionDetector = new MotionDetector(this, (SurfaceView) findViewById(R.id.surfaceView));
        motionDetector.setMotionDetectorCallback(new MotionDetectorCallback() {
            @Override
            public void onMotionDetected() {
                if (!pauseTimer) {
                    jumps++;
                    txtJumps.setText(jumps + "");
                }
            }

            @Override
            public void onTooDark() {
                txtJumps.setText("Too dark here");
            }
        });

        button_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                button_start.setText("START");
                pauseTimer = false;
                T.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                txtTime.setText(secondsToString(count));
                                if (!pauseTimer) count++;
                            }
                        });
                    }
                }, 1000, 1000);
            }
        });

        button_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_start.setText("RESUME");
                pauseTimer = true;
            }
        });

        button_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //implement finish
            }
        });

        ////// Config Options
        //motionDetector.setCheckInterval(500);
        //motionDetector.setLeniency(20);
        //motionDetector.setMinLuma(1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        motionDetector.onResume();

        if (motionDetector.checkCameraHardware()) {
            //txtStatus.setText("Camera found");
        } else {
            //txtStatus.setText("No camera available");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        motionDetector.onPause();
    }

    private String secondsToString(int pTime) {
        return String.format("%02d:%02d", pTime / 60, pTime % 60);
    }
}