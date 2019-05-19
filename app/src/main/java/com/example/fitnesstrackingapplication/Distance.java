package com.example.fitnesstrackingapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.EventListener;

public class Distance extends AppCompatActivity implements SensorEventListener {

    TextView user;
    TextView dist;
    DatabaseHelper db;
    SensorManager sensorManager;
    Sensor step;
    int stepsTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distance);

        stepsTaken = 0;
        db = new DatabaseHelper(this);
        user = findViewById(R.id.loginUser);
        dist = findViewById(R.id.Distance);
        user.setText("User: " + getIntent().getStringExtra("Username"));
        dist.setText("Distance: " + getIntent().getIntExtra("Distance", -1));

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        step = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, step, sensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] == 1.0f) {
            stepsTaken += 1;
        }
        dist.setText("Distance: " + Integer.toString(stepsTaken));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
