package primitives;

import static primitives.Util.isZero;

import geometries.Intersectable.*;
import  primitives.Vector;

import java.util.List;
import java.util.Objects;

public class Ray {
    Point p;
    Vector v;
    /*************** ctor *****************/
    /**
     * ctor that gets 2 parameteres
     * @param p2
     * @param v2
     */
    public Ray(Vector v2, Point p2) {
        super();
        p = p2;
        v = v2.normalize();
    }
    public Ray( Point p2,Vector v2) {
        super();
        p = p2;
        v = v2.normalize();
    }


    public Point getPoint(){return  this.p;}
    public Vector getVector(){return  this.v;}
    /**
     * this function gets a list of (intersection) points,
     * and returns the closest point from them to p0-beginning of this ray.
     * @param points
     * @return closestP
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }


    /**
     * same as the function "findClosestPoint", but works with GeoPoints.
     * @param points
     * @return the GeoPoint in which its point is the closest to p0 of the ray.
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points)
    {
        if (points==null)//if the list is empty
            return null;

        GeoPoint closestP=points.get(0);			//take the 1st point in the beginning. point and geometry.
        double min=p.distance(points.get(0).point);

        for(int i=0; i<points.size(); i++) 		//move on all the points
        {
            if (p.distance(points.get(i).point)<min) //change the closest point if the dis < min
            {
                min=p.distance(points.get(i).point);
                closestP=points.get(i);
            }
        }
        return closestP;	//return the closest point(and the geometry it intersects)-with min distance from p0.

    }
    /*************** Admin *****************/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Ray)) return false;
        Ray other = (Ray)obj;
        return this.v.equals(other.v) && this.p.equals(other.p);
    }

    public Point getPoint(double t)
    {
        Point tmp=new Point(p.dPoint.d1 ,p.dPoint.d2,p.dPoint.d3);
        return isZero(t) ? p : tmp.add(v.scale(t));//takes the beginning of the ray and adds the vector*scalar point that we get.
    }

    @Override
    public String toString()
    {
        return "Ray [Point=" + p + ", Vector=" +v + "]";
    }
    @Override
    public int hashCode() {
        return Objects.hash(p, v);
    }
}
