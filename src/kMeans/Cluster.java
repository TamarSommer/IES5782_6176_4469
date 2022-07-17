package kMeans;

import java.util.ArrayList;
import java.util.List;

public class Cluster
{

    public List<KPoint> points;
    public KPoint centroid;
    public int id;

    //Creates a new Cluster
    public Cluster(int id)
    {
        this.id = id;
        this.points = new ArrayList<KPoint>();
        this.centroid = null;
    }

    public List<KPoint> getPoints()
    {
        return points;
    }

    public void addPoint(List<KPoint> points)
    {
        for(KPoint p :points)
        {
            points.add(p);
        }
    }
    public void addPoint(KPoint p)
    {
        points.add(p);
    }

    public void setPoints(List<KPoint> points)
    {
        this.points = points;
    }

    public KPoint getCentroid()
    {
        return centroid;
    }

    public void setCentroid(KPoint centroid)
    {
        this.centroid = centroid;
    }

    public int getId()
    {
        return id;

    }

    public void clear()
    {
        points.clear();
    }

}