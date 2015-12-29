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
import org.zhengshuai.innersensor.graph.ProximityGraph;
import org.zhengshuai.innersensor.util.MyResponseHandler;
import org.zhengshuai.innersensor.util.Utils;

/**
 * Created by zhengshuai on 12/26/15.
 */
public class ProximityActivity  extends Activity implements SensorEventListener{

    private Sensor proximity;
    private SensorManager sensorManager;
    private TextView proximityTextView;

    private LinearLayout layout;
    private static GraphicalView view;
    private Point xPoint;
    private ProximityGraph proximityGraph = new ProximityGraph();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proximity_layout);
        Intent intent = getIntent();

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, proximity, sensorManager.SENSOR_DELAY_NORMAL);
        proximityTextView = (TextView)findViewById(R.id.proximity_sensor_text);

        //initialize the graph
        layout = (LinearLayout) findViewById(R.id.proximity_sensor_graph);
        // get the graphical view
        view = proximityGraph.getProximityView(this);
        // add the view to layout
        layout.addView(view);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        proximityTextView.setText("Proximity Value: \n" + event.values[0]);

        double x = event.values[0];
        long timestamp = System.currentTimeMillis();

        xPoint = new Point(timestamp, x);
        proximityGraph.addNewPoint(xPoint);
        view.repaint();

        //Server tasks

        String phoneName = Build.MODEL;
        String sendTime = Utils.getCurrentTimeFormat("dd-MM-yyyy hh:mm:ss");
        String sensorName = "Proximity";
        String origin = "origin";

        // define url
        String url = "http://10.31.20.97:8080/index.jsp";

        // create async http client
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        // new a response handler
        MyResponseHandler myResponseHandler = new MyResponseHandler();

        // sent data
        RequestParams requestParams = new RequestParams();
        requestParams.add("Sensor", String.valueOf(sensorName));
        requestParams.add("PhoneName", String.valueOf(phoneName));
        requestParams.add("SendTime", String.valueOf(sendTime));
        requestParams.add("Origin", String.valueOf(origin));
        requestParams.add("ProximityValue", String.valueOf(x));

        //post
        asyncHttpClient.post(url, requestParams, myResponseHandler);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
