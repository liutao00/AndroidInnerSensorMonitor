package org.zhengshuai.innersensor.entity;

/**
 * Created by zhengshuai on 12/26/15.
 */
public class Point {
    private long timestamp;
    private double value;

    public Point(long timestamp, double value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Point{" +
                "timestamp=" + timestamp +
                ", value=" + value +
                '}';
    }
}
