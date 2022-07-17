package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Sphere extends Geometry {
    Point center;
    double radius;


    /*************** ctor *****************/
    /**
     * ctor that gets 2 parameters
     * @param center
     * @param radius
     */
    public Sphere(Point center, double radius) {
        super();
        if(isZero(radius) || radius < 0)
            throw new IllegalArgumentException("Zero or negative radius");
        this.center = center;
        this.radius = radius;
    }

    /*************** normalize *****************/
    /**
     * @return the normal
     */
    @Override
    public Vector getNormal(Point p)
    {
        Vector N = p.subtract(center);
        return N.normalize();//the normal to sphere is the subtraction of the given point from the center. we get the normal vector
    }
    /*************** gets *****************/
    /**
     * @return the center
     */
    public Point getCenter() {
        return center;
    }

    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /*************** admin *****************/
    @Override
    public String toString() {
        return "Sphere [center=" + center + ", radius=" + radius + "]";
    }

    /*************** intersections *****************/
    /**
     * @param ray
     * @return a list of GeoPoints- intersections of the ray with the sphere, and this sphere
     */
    //@Override
    /*public List<GeoPoint> findGeoIntersectionsHelper(Ray ray)
    {
        double r = this.radius;

        // Special case: if point p0 == center, that mean that all we need to calculate
        // is the radios mult scalar with the direction, and add p0
        if (center.equals(ray.getPoint())) {
            LinkedList<GeoPoint> result = new LinkedList<GeoPoint>();
            result.add(new GeoPoint(this, ray.getPoint(r)));
            return result;
        }

        Vector u = center.subtract(ray.getPoint());
        double tm = u.dotProduct(ray.getVector());
        double d = Math.sqrt(alignZero(u.lengthSquared() - tm * tm));

        if (d >= r) //also In case the cut is tangent to the object still return null - d = r
            return null;

        double th = Math.sqrt(r * r - d * d);
        double t1 = tm + th;
        double t2 = tm - th;

        if(alignZero(t1) > 0 || alignZero(t2) > 0){
            LinkedList<GeoPoint> result = new LinkedList<GeoPoint>();
            if(alignZero(t1) > 0){
                Point p1 = ray.getPoint(t1);
                result.add(new GeoPoint(this, p1));
            }
            if(alignZero(t2) > 0){
                Point p2 = ray.getPoint(t2);
                result.add(new GeoPoint(this, p2));
            }
            return result;
        }
        else { //In case there are no intersections points
            return null;
        }

    }*/
    public List<GeoPoint> findGeoIntersectionsParticular(Ray ray) {
        if (ray.getPoint().equals(this.center)) {
            return List.of(new GeoPoint(this, ray.getPoint(this.radius)));
        } else {
            Vector u = this.center.subtract(ray.getPoint());
            double tM = Util.alignZero(ray.getVector().dotProduct(u));
            double d = Util.alignZero(Math.sqrt(u.length() * u.length() - tM * tM));
            double tH = Util.alignZero(Math.sqrt(this.radius * this.radius - d * d));
            double t1 = Util.alignZero(tM + tH);
            double t2 = Util.alignZero(tM - tH);
            if (d > this.radius) {
                return null;
            } else if (t1 <= 0.0D && t2 <= 0.0D) {
                return null;
            } else if (t1 > 0.0D && t2 > 0.0D) {
                return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
            } else {
                return t1 > 0.0D ? List.of(new GeoPoint(this, ray.getPoint(t1))) : List.of(new GeoPoint(this, ray.getPoint(t2)));
            }
        }
    }

    protected void findMinMaxParticular() {
        this.minX = this.center.getD1() - this.radius;
        this.maxX = this.center.getD1()+ this.radius;
        this.minY = this.center.getD2() - this.radius;
        this.maxY = this.center.getD2()+ this.radius;
        this.minZ = this.center.getD3()- this.radius;
        this.maxZ = this.center.getD3() + this.radius;
    }

    public Point getKPointPosition() {
        return this.center;
    }


}