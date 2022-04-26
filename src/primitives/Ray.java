package primitives;

import static primitives.Util.isZero;
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
    public Point findClosestPoint(List<Point> points)
    {
        if(points== null){
            return null;
        }

        Point result =null;
        double closestDistance = Double.MAX_VALUE;

        for (Point p: points) {
            double temp = p.distance(p);
            if(temp < closestDistance){
                closestDistance =temp;
                result =p;
            }
        }

        return  result;
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
