package org.zhengshuai.innersensor.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.zhengshuai.innersensor.R;

public class MainActivity extends AppCompatActivity {

    private Button gravityBtn;
    private Button accelerometerBtn;
    private Button proximityBtn;
    private Button lightBtn;
    private Button gpsBtn;
    private Button temperatureBtn;
    private Button pressureBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gravityBtn = (Button) findViewById(R.id.btn_gravity);
        accelerometerBtn = (Button) findViewById(R.id.btn_accelerometer);
        proximityBtn = (Button)findViewById(R.id.btn_proximity);
        lightBtn = (Button) findViewById(R.id.btn_light);
        gpsBtn = (Button) findViewById(R.id.btn_gps);
        temperatureBtn = (Button) findViewById(R.id.btn_temp);
        pressureBtn = (Button)findViewById(R.id.btn_pressure);
    }

    public void gravityHandler(View view){
        // when button was clicked, change activity to target activity
        Intent myIntent = new Intent(MainActivity.this, GravityActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
    public void proximityHandler(View view){
        Intent myIntent = new Intent(MainActivity.this, ProximityActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
    public void accelerometerHandler(View view){
        Intent myIntent = new Intent(MainActivity.this, AccelerometerActivity.class);
        MainActivity.this.startActivity(myIntent);

    }
    public void lightHandler(View view){
        Intent myIntent = new Intent(MainActivity.this, LightActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void gpsHandler(View view){
        Intent myIntent = new Intent(MainActivity.this, GpsActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void temperatureHandler(View view){
        Intent myIntent = new Intent(MainActivity.this, TemperatureActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    public void pressureHandler(View view){
        Intent myIntent = new Intent(MainActivity.this, PressureActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
}
