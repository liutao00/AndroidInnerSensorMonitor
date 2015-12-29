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
import org.zhengshuai.innersensor.graph.GravityGraph;
import org.zhengshuai.innersensor.util.MyResponseHandler;
import org.zhengshuai.innersensor.util.Utils;

/**
 * Created by zhengshuai on 12/26/15.
 */
public class GravityActivity extends Activity implements SensorEventListener {


    private Sensor gravity;
    private SensorManager sensorManager;
    private TextView gravityTextView;

    private LinearLayout layout;
    private static GraphicalView view;
    private Point xPoint;
    private GravityGraph gravityGraph = new GravityGraph();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gravity_layout);
        Intent intent = getIntent();

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(this, gravity, sensorManager.SENSOR_DELAY_NORMAL);
        gravityTextView = (TextView)findViewById(R.id.gravity_sensor_text);

        //initialize the graph
        layout = (LinearLayout) findViewById(R.id.gravity_sensor_graph);
        // get the graphical view
        view = gravityGraph.getGravityView(this);
        // add the view to layout
        layout.addView(view);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        gravityTextView.setText("Gravity Value: \n" + event.values[0]);

        double x = event.values[0];
        long timestamp = System.currentTimeMillis();

        xPoint = new Point(timestamp, x);
        gravityGraph.addNewPoint(xPoint);
        view.repaint();

        //Server tasks

        String phoneName = Build.MODEL;
        String sendTime = Utils.getCurrentTimeFormat("dd-MM-yyyy hh:mm:ss");
        String sensorName = "Gravity";
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
        requestParams.add("GravityValue", String.valueOf(x));

        //post
        asyncHttpClient.post(url, requestParams, myResponseHandler);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
