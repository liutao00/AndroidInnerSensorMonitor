package org.zhengshuai.innersensor.graph;

import android.content.Context;
import android.graphics.Color;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.zhengshuai.innersensor.entity.Point;

/**
 * Created by zhengshuai on 12/26/15.
 */
public class GpsGraph {
    private GraphicalView view;

    private TimeSeries xSeries = new TimeSeries("Latitude");
    private TimeSeries ySeries = new TimeSeries("Longitude");

    private XYMultipleSeriesDataset mSeriesDataset = new XYMultipleSeriesDataset();


    private XYSeriesRenderer xRender = new XYSeriesRenderer();
    private XYSeriesRenderer yRender = new XYSeriesRenderer();


    private XYMultipleSeriesRenderer mSeriesRenderer = new XYMultipleSeriesRenderer();

    public GpsGraph() {
        // add line to multi dataset
        mSeriesDataset.addSeries(xSeries);
        mSeriesDataset.addSeries(ySeries);


        // customize for line X
        xRender.setColor(Color.RED);
        xRender.setPointStyle(PointStyle.SQUARE);
        xRender.setFillPoints(true);
        xRender.setLineWidth(2);

        // customize for line Y
        yRender.setColor(Color.BLUE);
        yRender.setPointStyle(PointStyle.SQUARE);
        yRender.setFillPoints(true);
        yRender.setLineWidth(2);



        // enable zoom
        mSeriesRenderer.setZoomButtonsVisible(true);
        mSeriesRenderer.setXTitle("Time");
        mSeriesRenderer.setYTitle("GPS Value");
        mSeriesRenderer.setChartTitle("t vs (latitude, longitude)");
        mSeriesRenderer.setXLabels(0);


        // add renderer to multiRenderer
        mSeriesRenderer.addSeriesRenderer(xRender);
        mSeriesRenderer.addSeriesRenderer(yRender);

    }

    public GraphicalView getGpsView(Context context){
        view = ChartFactory.getLineChartView(context, mSeriesDataset, mSeriesRenderer);
        return view;
    }

    public void addNewPoint(Point latitude, Point longitude){
        xSeries.add(latitude.getTimestamp(), longitude.getValue());
        ySeries.add(latitude.getTimestamp(), longitude.getValue());

    }
}
