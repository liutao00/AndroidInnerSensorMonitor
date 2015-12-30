package org.zhengshuai.innersensor.activity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;

import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import org.achartengine.GraphicalView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.zhengshuai.innersensor.R;
import org.zhengshuai.innersensor.entity.Point;
import org.zhengshuai.innersensor.graph.AccelerometerGraph;
import org.zhengshuai.innersensor.util.MyResponseHandler;
import org.zhengshuai.innersensor.util.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhengshuai on 12/26/15.
 */
public class AccelerometerActivity extends Activity implements SensorEventListener {


    private Sensor accelerometer;
    private SensorManager sensorManager;
    private TextView accelerationTextView;

    private LinearLayout layout;
    private static GraphicalView view;
    private AccelerometerGraph accelerometerGraph = new AccelerometerGraph();
    private Point xPoint;
    private Point yPoint;
    private Point zPoint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accelerometer_layout);
        Intent intent = getIntent();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, sensorManager.SENSOR_DELAY_NORMAL);
        accelerationTextView = (TextView) findViewById(R.id.accelerometer_sensor_text);

        // initialize the graph
        layout = (LinearLayout) findViewById(R.id.accelerometer_sensor_graph);
        // get graph view
        view = accelerometerGraph.getGraphView(this);
        // add this view to linear layout
        layout.addView(view);


    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        // show information in TextView
        accelerationTextView.setText("Accelerometer Value: \n" + "X: " + event.values[0] + "\n" + "Y: " + event.values[1] + "\n" + "Z: " + event.values[2]);


        final double x = event.values[0];
        final double y = event.values[1];
        final double z = event.values[2];
        final long timestamp = System.currentTimeMillis();

        xPoint = new Point(timestamp, x);
        yPoint = new Point(timestamp, y);
        zPoint = new Point(timestamp, z);


        // add points to chart
        accelerometerGraph.addNewPoint(xPoint, yPoint, zPoint);
        // repaint the view
        view.repaint();

        //Server tasks
        //Server tasks

        String phoneName = Build.MODEL;
        String sendTime = Utils.getCurrentTimeFormat("dd-MM-yyyy hh:mm:ss");
        String sensorName = "Accelerometer";

        // encryption
        String origin = "ISEPMobileProject";


        // define url
        String url = "http://innersensorserver-zhengshuai.rhcloud.com/InnerSensorRestServer/webapi/server/accelerometer";

        // create async http client
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        // new a response handler
        MyResponseHandler myResponseHandler = new MyResponseHandler();

        // sent data
        RequestParams requestParams = new RequestParams();
        requestParams.put("Sensor", String.valueOf(sensorName));
        requestParams.put("PhoneName", String.valueOf(phoneName));
        requestParams.put("SendTime", String.valueOf(sendTime));
        requestParams.put("xValue", String.valueOf(x));
        requestParams.put("yValue", String.valueOf(y));
        requestParams.put("zValue", String.valueOf(z));
        requestParams.put("Origin", String.valueOf(origin));

        asyncHttpClient.get(url, requestParams, myResponseHandler);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}

