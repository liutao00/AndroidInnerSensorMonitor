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
public class GravityGraph {

    private GraphicalView graphicalView;
    private TimeSeries xSeries = new TimeSeries("Gravity");
    private XYMultipleSeriesDataset mSeriesDataset = new XYMultipleSeriesDataset();
    private XYSeriesRenderer xRenderer = new XYSeriesRenderer();
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

    public GravityGraph() {

        // add line to line dataset
        mSeriesDataset.addSeries(xSeries);

        // customize the line
        xRenderer.setColor(Color.RED);
        xRenderer.setPointStyle(PointStyle.SQUARE);
        xRenderer.setFillPoints(true);
        xRenderer.setLineWidth(2);

        // enable zoom
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setXTitle("Time");
        mRenderer.setYTitle("Gravity Value");
        mRenderer.setChartTitle("Gravity vs Time");
        mRenderer.setXLabels(0);

        // add renderer to multi renderer
        mRenderer.addSeriesRenderer(xRenderer);

    }

    public GraphicalView getGravityView(Context context){
        // use factory to get a line chart
        graphicalView = ChartFactory.getLineChartView(context, mSeriesDataset, mRenderer);
        return graphicalView;
    }

    public void addNewPoint(Point point){
        // add new point to the line
        xSeries.add(point.getTimestamp(), point.getValue());
    }
}
