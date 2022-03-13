package geometries;

import primitives.Vector;

import primitives.Point;
import primitives.Ray;

public class Plane implements Geometry {
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
        this.normal = v1.crossProduct(v2).normalize();
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
        return normal;
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


}
