/*
 * Created by Sujoy Datta. Copyright (c) 2018. All rights reserved.
 *
 * To the person who is reading this..
 * When you finally understand how this works, please do explain it to me too at sujoydatta26@gmail.com
 * P.S.: In case you are planning to use this without mentioning me, you will be met with mean judgemental looks and sarcastic comments.
 */

package com.morningstar.mbreathinternship;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class assignmentTwo extends AppCompatActivity implements SensorEventListener {

    int SENSOR_NUMBER = 0;
    private TextView textView_light, textView_pressure, textView_temperature, textView_humidity;
    private Button btnStart;
    private Sensor lightSensor, tempSensor, humiditySensor, pressureSensor;
    private SensorManager sensorManager;
    private boolean isSensorStarted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_two);

        textView_humidity = findViewById(R.id.tvHumidity);
        textView_light = findViewById(R.id.tvLight);
        textView_pressure = findViewById(R.id.tvPressure);
        textView_temperature = findViewById(R.id.tvTemp);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        btnStart = findViewById(R.id.btn_start_tracking);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSensors();
            }
        });
    }

    private void startSensors() {
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (lightSensor != null) {
            sensorManager.registerListener(assignmentTwo.this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            isSensorStarted = true;
        } else {
            textView_light.setText("Light Sensor is not supported!");
        }

        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (tempSensor != null) {
            sensorManager.registerListener(assignmentTwo.this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
            isSensorStarted = true;
        } else {
            textView_temperature.setText("Temperature sensor not supported!");
        }

        humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if (humiditySensor != null) {
            sensorManager.registerListener(assignmentTwo.this, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
            isSensorStarted = true;
        } else {
            textView_humidity.setText("Humidity sensor is not supported!");
        }

        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if (pressureSensor != null) {
            sensorManager.registerListener(assignmentTwo.this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
            isSensorStarted = true;
        } else {
            textView_pressure.setText("Pressure Sensor is not supported!");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isSensorStarted) {
            startSensors();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_LIGHT) {
            textView_light.setText("Light: " + event.values[0]);
        } else if (sensor.getType() == Sensor.TYPE_PRESSURE) {
            textView_pressure.setText("Pressure: " + event.values[0]);
        } else if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            textView_temperature.setText("Temperature: " + event.values[0]);
        } else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            textView_humidity.setText("Humidity: " + event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void plotTemp(View view) {
        Intent intent = new Intent(assignmentTwo.this, PlotActivity.class);
        SENSOR_NUMBER = 1;
        intent.putExtra("Sensor number", SENSOR_NUMBER);
        startActivity(intent);
    }

    public void plotHumd(View view) {
        Intent intent = new Intent(assignmentTwo.this, PlotActivity.class);
        SENSOR_NUMBER = 4;
        intent.putExtra("Sensor number", SENSOR_NUMBER);
        startActivity(intent);
    }

    public void plotPress(View view) {
        Intent intent = new Intent(assignmentTwo.this, PlotActivity.class);
        SENSOR_NUMBER = 3;
        intent.putExtra("Sensor number", SENSOR_NUMBER);
        startActivity(intent);
    }

    public void plotLight(View view) {
        Intent intent = new Intent(assignmentTwo.this, PlotActivity.class);
        SENSOR_NUMBER = 2;
        intent.putExtra("Sensor number", SENSOR_NUMBER);
        startActivity(intent);
    }
}
