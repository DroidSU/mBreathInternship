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
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

//This class has not been properly implemented yet

public class PlotActivity extends AppCompatActivity implements SensorEventListener {

    int SENSOR_NUMBER = 0;
    boolean shouldPlotData = true;
    LineChart mChart;
    private Thread thread;

    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        Toast.makeText(this, "This class has not been properly implemented yet due to shortage of time!", Toast.LENGTH_LONG).show();

        Intent intent = getIntent();
        SENSOR_NUMBER = intent.getIntExtra("Sensor number", 0);

        mChart = new LineChart(this);
        mChart.getDescription().setEnabled(true);
        mChart.getDescription().setText("Real time data plotting");
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setTouchEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setBackgroundColor(Color.BLACK);

        LineData lineData = new LineData();
        lineData.setValueTextColor(Color.DKGRAY);
        mChart.setData(lineData);

        //Setting and enabling the horizontal axis
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextColor(Color.DKGRAY);
        xAxis.setDrawGridLines(true);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setEnabled(true);

        //Setting and enabling the left Y axis
        YAxis leftY = mChart.getAxisLeft();
        leftY.setTextColor(Color.DKGRAY);
        leftY.setDrawGridLines(true);
        leftY.setEnabled(true);

        //Disabling the right Y-axis
        YAxis rightY = mChart.getAxisRight();
        rightY.setEnabled(false);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        switch (SENSOR_NUMBER) {
            case 1:
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                break;
            case 2:
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                break;
            case 3:
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
                break;
            case 4:
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
                break;
            default:
                Toast.makeText(this, "No Sensors detected!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sensor != null) {
            sensorManager.registerListener(PlotActivity.this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            startPlot();
        } else {
            Toast.makeText(this, "This sensor is not detected!", Toast.LENGTH_SHORT).show();
        }
    }

    private void startPlot() {
        if (thread != null) {
            thread.interrupt();
        }
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    shouldPlotData = true;
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (shouldPlotData) {
            addEntry(event);
            shouldPlotData = false;
        }
    }

    private void addEntry(SensorEvent event) {
        LineData data = mChart.getData();
        if (data != null) {
            ILineDataSet dataSet = data.getDataSetByIndex(0);
            if (dataSet == null) {
                dataSet = createNewDataSet();
                data.addDataSet(dataSet);
            }

            data.addEntry(new Entry(dataSet.getEntryCount(), event.values[0]), 0);
            data.notifyDataChanged();
            mChart.setMaxVisibleValueCount(150);
            mChart.moveViewToX(data.getEntryCount());
        }
    }

    private ILineDataSet createNewDataSet() {
        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setLineWidth(5f);
        set.setColor(Color.DKGRAY);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setCubicIntensity(0.2f);
        return set;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (shouldPlotData) {
            sensorManager.registerListener(PlotActivity.this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
        shouldPlotData = false;
    }
}
