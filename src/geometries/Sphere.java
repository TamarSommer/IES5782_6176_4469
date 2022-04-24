package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

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
        return center.subtract(p).normalize();//the normal to sphere is the subtraction of the given point from the center. we get the normal vector
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
    @Override
    public List<Point> findIntersections(Ray ray)
    {
        if (ray.getPoint().equals(center)) // if the begin of the ray in the center, the point, is on the radius
            return List.of(ray.getPoint(radius));
        Vector u = center.subtract(ray.getPoint());
        double tM = Util.alignZero(ray.getVector().dotProduct(u));
        double d = Util.alignZero(Math.sqrt(u.length()*u.length()- tM * tM));
        double tH = Util.alignZero(Math.sqrt(radius*radius - d*d));
        double t1 = Util.alignZero(tM+tH);
        double t2 = Util.alignZero(tM-tH);
        if (d > radius)
            return null; // there are no instructions
        if (t1 <=0 && t2<=0)
            return null;
        if (t1 > 0 && t2 >0)
            return List.of(ray.getPoint(t1),ray.getPoint(t2));
        if (t1 > 0)
        {
            return List.of(ray.getPoint(t1));
        }
        else
            return List.of(ray.getPoint(t2));

    }
}