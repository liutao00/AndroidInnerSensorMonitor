package org.zhengshuai.innersensor.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import org.achartengine.GraphicalView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.zhengshuai.innersensor.R;
import org.zhengshuai.innersensor.entity.Point;
import org.zhengshuai.innersensor.graph.GpsGraph;
import org.zhengshuai.innersensor.util.MyResponseHandler;
import org.zhengshuai.innersensor.util.Utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by zhengshuai on 12/26/15.
 */
public class GpsActivity extends Activity implements LocationListener {

    private TextView gpsTextView;
    private LocationManager locationManager;

    private LinearLayout layout;
    private static GraphicalView view;
    private GpsGraph gpsGraph = new GpsGraph();
    private Point latitudePoint;
    private Point longitudePoint;

    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_layout);
        Intent intent = getIntent();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);

        gpsTextView = (TextView) findViewById(R.id.gps_sensor_text);

        // initialize the graph
        layout = (LinearLayout) findViewById(R.id.gps_sensor_graph);
        // get graph view
        view = gpsGraph.getGpsView(this);
        // add this view to linear layout
        layout.addView(view);

    }


    @Override
    public void onLocationChanged(Location loc) {
        gpsTextView.setText("Location changed: Lat: " + loc.getLatitude() + " Lng: "
                + loc.getLongitude());

        String longitude = "Longitude: " + loc.getLongitude();
        String latitude = "Latitude: " + loc.getLatitude();

        final double y = loc.getLongitude();
        final double x = loc.getLatitude();
        final long timestamp = System.currentTimeMillis();

        latitudePoint = new Point(timestamp, x);
        longitudePoint = new Point(timestamp, y);

        gpsGraph.addNewPoint(latitudePoint, longitudePoint);
        view.repaint();

        //Server tasks

        String phoneName = Build.MODEL;
        String sendTime = Utils.getCurrentTimeFormat("dd-MM-yyyy hh:mm:ss");
        String sensorName = "Gps";
        String origin = "origin";

        // define url
        String url = "http://10.31.20.97:8080/server";

        // create async http client
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        // new a response handler
        MyResponseHandler myResponseHandler = new MyResponseHandler();

        // sent data
        RequestParams requestParams = new RequestParams();
        requestParams.add("Sensor", String.valueOf(sensorName));
        requestParams.add("PhoneName", String.valueOf(phoneName));
        requestParams.add("SendTime", String.valueOf(sendTime));
        requestParams.add("Latitude", String.valueOf(x));
        requestParams.add("Longitude", String.valueOf(y));
        requestParams.add("Origin", origin);

        asyncHttpClient.post(url, requestParams, myResponseHandler);


    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
