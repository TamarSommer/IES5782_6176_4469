package geometries;

import primitives.Vector;

import primitives.Point;
import primitives.Ray;

import java.util.List;

import static primitives.Util.isZero;

public class Plane implements Geometry {
    final Point q0;
    final Vector normal;


    /*************** ctors *****************/
    /**
     * ctor that gets 2 parameters
     * @param q2
     * @param normal2
     */

    public Plane(Vector normal2,Point q2) {
        this.normal = normal2.normalize();
        this.q0 = q2;
    }
    /**
     * ctor that gets 3 points
     * @param p1
     * @param p2
     * @param p3
     */
    public Plane(Point p1,Point p2,Point p3) {
        /*this.q0 = p1;
        Vector v1 = (p2.subtract(p3));
        Vector v2 = (p1.subtract(p3));
        this.normal = v2.crossProduct(v1).normalize();
        this.normal = v1.crossProduct(v2).normalize();*/
        q0 = p1;
        Vector U = (Vector) p2.subtract(p1);
        Vector V = (Vector) p3.subtract(p1);
        Vector N = U.crossProduct(V);
        normal = N.normalize();
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
        return getNormal();
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
        Point P0 = ray.getPoint();
        Vector v = ray.getVector();

        Vector n = normal;

        if(q0.equals(P0)){
            return  null;
        }

        Vector P0_Q0 = q0.subtract(P0);

        //numerator
        double nP0Q0  = alignZero(n.dotProduct(P0_Q0));

        //
        if (isZero(nP0Q0 )){
            return null;
        }

        //denominator
        double nv = alignZero(n.dotProduct(v));

        // ray is lying in the plane axis
        if(isZero(nv)){
            return null;
        }

        double  t = alignZero(nP0Q0  / nv);

        if (t <=0){
            return  null;
        }

        Point point = ray.getPoint(t);

        return List.of(point);
    }

}
