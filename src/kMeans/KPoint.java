package kMeans;

import java.util.Random;

import geometries.*;
import primitives.*;

public class KPoint {
    private Geometry geometry;
    private Point KPositionPoint;
    private int clusterNumber = 0;

    public KPoint(Geometry g)
    {
        this.geometry=g;
        this.KPositionPoint=g.getKPointPosition();
    }

    /**
     * @return the PositionPoint
     */
    public Point getKPositionPoint() {
        return KPositionPoint;
    }
    /**
     * @return the geometry
     */
    public Geometry getGeometry() {
        return geometry;
    }
    /**
     * @param geometry
     */
    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
    /**
     * @param positionPoint the point to set
     */
    public void setPositionPoint(Point positionPoint) {
        KPositionPoint = positionPoint;
    }
    /**
     * @return the clusterNumber
     */
    public int getClusterNumber() {
        return clusterNumber;
    }
    /**
     * @param cluster_number
     */
    public void setClusterNumber(int cluster_number) {
        this.clusterNumber = cluster_number;
    }
    //Distance between two position_points
    protected static double distance(KPoint p, KPoint centroid) {
        return p.getKPositionPoint().distanceSquared(centroid.getKPositionPoint());
    }
    //Creates random point
    protected static KPoint createRandomPoint() {
        return KPoint.createRandomPoint(Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);
    }
    protected static KPoint createRandomPoint(double min, double max) {
        Random r = new Random();
        double x = min + (max - min) * r.nextDouble();
        double y = min + (max - min) * r.nextDouble();
        double z = min + (max - min) * r.nextDouble();
        return new KPoint(new Sphere(new Point(x,y,z),1));
    }
}
