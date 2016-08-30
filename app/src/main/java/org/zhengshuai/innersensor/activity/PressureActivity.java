package org.zhengshuai.innersensor.activity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import org.achartengine.GraphicalView;
import org.zhengshuai.innersensor.R;
import org.zhengshuai.innersensor.entity.Point;
import org.zhengshuai.innersensor.graph.PressureGraph;
import org.zhengshuai.innersensor.graph.ProximityGraph;
import org.zhengshuai.innersensor.util.MyResponseHandler;
import org.zhengshuai.innersensor.util.Utils;

/**
 * Created by zhengshuai on 12/26/15.
 */
public class PressureActivity  extends Activity implements SensorEventListener{

    private Sensor pressure;
    private SensorManager sensorManager;
    private TextView pressureTextView;

    private LinearLayout layout;
    private static GraphicalView view;
    private Point xPoint;
    private PressureGraph pressureGraph = new PressureGraph();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pressure_layout);
        Intent intent = getIntent();

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        sensorManager.registerListener(this, pressure, sensorManager.SENSOR_DELAY_NORMAL);
        pressureTextView = (TextView)findViewById(R.id.pressure_sensor_text);

        //initialize the graph
        layout = (LinearLayout) findViewById(R.id.pressure_sensor_graph);
        // get the graphical view
        view = pressureGraph.getPressureView(this);
        // add the view to layout
        layout.addView(view);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        pressureTextView.setText("Pressure Value: \n" + event.values[0]);

        double x = event.values[0];
        long timestamp = System.currentTimeMillis();

        xPoint = new Point(timestamp, x);
        pressureGraph.addNewPoint(xPoint);
        view.repaint();

       /* //Server tasks

        String phoneName = Build.MODEL;
        String sendTime = Utils.getCurrentTimeFormat("dd-MM-yyyy hh:mm:ss");
        String sensorName = "Pressure";

        // encryption
        String origin = "ISEPMobileProject";


        // define url
        String url = "http://innersensorserver-zhengshuai.rhcloud.com/InnerSensorRestServer/webapi/server/proximity";

        // create async http client
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        // new a response handler
        MyResponseHandler myResponseHandler = new MyResponseHandler();

        // sent data
        RequestParams requestParams = new RequestParams();
        requestParams.put("Sensor", String.valueOf(sensorName));
        requestParams.put("PhoneName", String.valueOf(phoneName));
        requestParams.put("SendTime", String.valueOf(sendTime));
        requestParams.put("PressureValue", String.valueOf(x));
        requestParams.put("Origin", String.valueOf(origin));

        //post
        asyncHttpClient.get(url, requestParams, myResponseHandler);*/
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
