package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;
import java.util.LinkedList;

public class Plane extends Geometry {
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


    /*************** intersections *****************/
    /**
     * @param ray
     * @return a list of GeoPoints- intersections of the ray with the plane, and this plane
     */

    public List<GeoPoint> findGeoIntersectionsParticular(Ray ray) throws IllegalArgumentException {
        double nv = this.normal.dotProduct(ray.getVector());
        if (Util.isZero(nv)) {
            return null;
        } else {
            try {
                Vector pSubtractP0 = this.q0.subtract(ray.getPoint());
                double t = Util.alignZero(this.normal.dotProduct(pSubtractP0) / nv);
                return t <= 0.0D ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
            } catch (Exception var7) {
                return null;
            }
        }
    }

    protected void findMinMaxParticular() {
        this.minX = -1.0D / 0.0;
        this.minY = -1.0D / 0.0;
        this.minZ = -1.0D / 0.0;
        this.maxX = 1.0D / 0.0;
        this.maxY = 1.0D / 0.0;
        this.maxZ = 1.0D / 0.0;
    }
    public Point getKPointPosition() {return this.q0;}

   /* public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        if (ray.getPoint().equals(q0) || isZero(this.normal.dotProduct(ray.getVector()))
                || isZero(this.normal.dotProduct(q0.subtract(ray.getPoint()))))
            return null;

        double t = (this.normal.dotProduct(q0.subtract(ray.getPoint()))) / (this.normal.dotProduct(ray.getVector()));
        if (t < 0) // In case there is no intersection with the plane return null
            return null;

        //In case there is intersection with the plane return the point
        GeoPoint p = new GeoPoint(this, ray.getPoint(t));
        LinkedList<GeoPoint> result = new LinkedList<GeoPoint>();
        result.add(p);
        return result;
    }*/
}