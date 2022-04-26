package geometries;

import primitives.Vector;

import primitives.Point;
import primitives.Ray;

import java.util.List;

import static primitives.Util.isZero;

public class Plane extends Geometry {
    Point q0;
    Vector normal;


    /*************** ctors *****************/
    /**
     * ctor that gets 2 parameters
     * @param q2
     * @param normal2
     */

    public Plane(Vector normal2,Point q2) {
        this.normal = normal2;
        this.q0 = q2;
    }
    /**
     * ctor that gets 3 points
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Point p1,Point p2,Point p3) {
        this.q0 = p1;
        Vector v1 = (p2.subtract(p3));
        Vector v2 = (p1.subtract(p3));
        this.normal = v2.crossProduct(v1).normalize();
    }
    /*************** get *****************/
    /**
     * @return q0 of the plane
     */
    public Point GetQ0() {
        return q0;
    }

    /**
     * @param p
     * @return the normal vector
     */

    @Override
    public Vector getNormal(Point p) {
        /*return new Vector(p.add(normal));*/
        return this.normal;
    }

    /*************** normalize *****************/
    /**
     * @return the normal vector
     */

    public Vector getNormal() {
        return normal;
    }


    @Override
    public String toString() {
        return "Plane [q0=" + q0 + ", normal=" + normal + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Ray))
            return false;
        Plane other = (Plane) obj;
        return this.normal.equals(other.normal)&&this.q0.equals(other.q0);
    }


    /*************** intersections *****************/
    /**
     * @param ray
     * @return a list of GeoPoints- intersections of the ray with the plane, and this plane
     */
    @Override
    public List<Point> findIntersections(Ray ray)
    {
        try {
            Vector vec=q0.subtract(ray.getPoint());//creating a new vector according to the point q0 and the starting point of the ray (P0)

            double t=normal.dotProduct(vec);//dot product between the vector we created and the normal of the plane

            if(isZero(normal.dotProduct(ray.getVector()))) //if the dot product equals 0 it means that the ray is parallel to the plane
                return null;
            t = t/(normal.dotProduct(ray.getVector()));

            if(t==0) //if the distance between the p0-the start point of the ray and the plane is 0, its not counted in the intersections list
                return null;//no intersections
            else if(t > 0) // the ray crosses the plane
            {
                Point p=ray.getPoint(t);//get the new point on the ray, multiplied by the scalar t. p is the intersection point.
                return List.of(new Point(p.dPoint));//if so, return the point- the intersection
            }
            else // the ray doesn't cross the plane
                return null;
        }
        catch(Exception ex) //catch exceptions in the vector creation
        {
            return null;
        }
    }

}
