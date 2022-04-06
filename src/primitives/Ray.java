package primitives;

import static primitives.Util.isZero;
import  primitives.Vector;

import java.util.List;

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
        if (points==null)//if the list is empty
            return null;

        Point closestP=points.get(0);			//take the 1st point in the beginning
        double min=p.distance(points.get(0));

        for(int i=0; i<points.size(); i++) 		//move on all the points
        {
            if (p.distance(points.get(i))<min) //change the closest point if the dis < min
            {
                min=p.distance(points.get(i));
                closestP=points.get(i);
            }
        }
        return closestP;					    //return is the closest point-with min distance from p0.
    }
    /*************** Admin *****************/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Ray other = (Ray) obj;
        if (v == null) {
            if (other.v != null)
                return false;
        } else if (!v.equals(other.v))
            return false;
        if (p == null) {
            if (other.p != null) {
                return false;
            }
        } else if (!p.equals(other.p)) {
            return false;
        }
        return true;
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

}
