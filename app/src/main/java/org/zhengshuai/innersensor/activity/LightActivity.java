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
import org.zhengshuai.innersensor.graph.LightGraph;
import org.zhengshuai.innersensor.util.MyResponseHandler;
import org.zhengshuai.innersensor.util.Utils;

/**
 * Created by zhengshuai on 12/26/15.
 */
public class LightActivity extends Activity implements SensorEventListener {

    private Sensor light;
    private SensorManager sensorManager;
    private TextView lightTextView;

    private LinearLayout layout;
    private static GraphicalView view;
    private Point point;
    private LightGraph lightGraph = new LightGraph();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light_layout);
        Intent intent = getIntent();

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, light, sensorManager.SENSOR_DELAY_NORMAL);
        lightTextView = (TextView)findViewById(R.id.light_sensor_text);

        //initialize the graph
        layout = (LinearLayout) findViewById(R.id.light_sensor_graph);
        // get the graphical view
        view = lightGraph.getLightView(this);
        // add the view to layout
        layout.addView(view);



    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        lightTextView.setText("Light Value: \n" + event.values[0]);
        double x = event.values[0];
        long timestamp = System.currentTimeMillis();

        point = new Point(timestamp, x);
        lightGraph.addNewPoint(point);
        view.repaint();

        //Server tasks

        String phoneName = Build.MODEL;
        String sendTime = Utils.getCurrentTimeFormat("dd-MM-yyyy hh:mm:ss");
        String sensorName = "Light";

        // define url
        String url = "http://innersensorserver-zhengshuai.rhcloud.com/InnerSensorRestServer/webapi/server/light";

        // create async http client
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        // new a response handler
        MyResponseHandler myResponseHandler = new MyResponseHandler();

        // sent data
        RequestParams requestParams = new RequestParams();
        requestParams.put("Sensor", String.valueOf(sensorName));
        requestParams.put("PhoneName", String.valueOf(phoneName));
        requestParams.put("SendTime", String.valueOf(sendTime));
        requestParams.put("LightValue", String.valueOf(x));

        //post
        asyncHttpClient.get(url, requestParams, myResponseHandler);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
