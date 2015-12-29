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
public class AccelerometerGraph {

    private GraphicalView view;

    private TimeSeries xSeries = new TimeSeries("X");
    private TimeSeries ySeries = new TimeSeries("Y");
    private TimeSeries zSeries = new TimeSeries("Z");

    private XYMultipleSeriesDataset mSeriesDataset = new XYMultipleSeriesDataset();


    private XYSeriesRenderer xRender = new XYSeriesRenderer();
    private XYSeriesRenderer yRender = new XYSeriesRenderer();
    private XYSeriesRenderer zRender = new XYSeriesRenderer();

    private XYMultipleSeriesRenderer mSeriesRenderer = new XYMultipleSeriesRenderer();


    public AccelerometerGraph() {

        // add line to multi dataset
        mSeriesDataset.addSeries(xSeries);
        mSeriesDataset.addSeries(ySeries);
        mSeriesDataset.addSeries(zSeries);

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

        // customize for line Z
        zRender.setColor(Color.YELLOW);
        zRender.setPointStyle(PointStyle.SQUARE);
        zRender.setFillPoints(true);
        zRender.setLineWidth(2);

        // enable zoom
        mSeriesRenderer.setZoomButtonsVisible(true);
        mSeriesRenderer.setXTitle("Time");
        mSeriesRenderer.setYTitle("Accelerometer Value");
        mSeriesRenderer.setChartTitle("t vs (x,y,z)");
        mSeriesRenderer.setXLabels(0);


        // add renderer to multiRenderer
        mSeriesRenderer.addSeriesRenderer(xRender);
        mSeriesRenderer.addSeriesRenderer(yRender);
        mSeriesRenderer.addSeriesRenderer(zRender);

    }

    public GraphicalView getGraphView(Context context){
        view = ChartFactory.getLineChartView(context, mSeriesDataset, mSeriesRenderer);
        return view;
    }

    public void addNewPoint(Point xPoint, Point yPoint, Point zPoint){
        xSeries.add(xPoint.getTimestamp(), xPoint.getValue());
        ySeries.add(yPoint.getTimestamp(), yPoint.getValue());
        zSeries.add(zPoint.getTimestamp(), zPoint.getValue());

    }
}
