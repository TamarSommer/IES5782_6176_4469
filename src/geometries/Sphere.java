package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

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
    @Override
    public List<Point> findIntersections(Ray ray)
    {
        Point P0 = ray.getPoint();
        Vector v = ray.getVector();

        if (P0.equals(center)) {
            return List.of(center.add(v.scale(radius)));
        }

        Vector u = center.subtract(P0);
        double tm = alignZero(v.dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));

        // no intersections : the ray direction is above the sphere
        if (d >= radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if (t1 > 0 && t2 > 0) {
            Point P1 = P0.add(v.scale(t1));
            Point P2 = P0.add(v.scale(t2));
            return List.of(P1, P2);
        }

        if (t1 > 0) {
            Point P1 = P0.add(v.scale(t1));
            return List.of(P1);
        }

        if (t2 > 0) {
            Point P2 = P0.add(v.scale(t2));
            return List.of(P2);
        }

        return null;

    }
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray)
    {
        /*Point P0 = ray.getPoint();
        Vector v = ray.getVector();

        if (P0.equals(center)) {
            return List.of(new GeoPoint(this,ray.getPoint(radius)));
        }

        Vector u = center.subtract(P0);
        double tm = alignZero(v.dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));

        // no intersections : the ray direction is above the sphere
        if (d >= radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        if (t1 > 0 && t2 > 0) {
            Point P1 = P0.add(v.scale(t1));
            Point P2 = P0.add(v.scale(t2));
            return (List<GeoPoint>) List.of(P1, P2);
        }

        if (t1 > 0) {
            Point P1 = P0.add(v.scale(t1));
            return (List<GeoPoint>) List.of(P1);
        }

        if (t2 > 0) {
            Point P2 = P0.add(v.scale(t2));

            return List.of(P2);
        }

        return null;*/
        Point p0 = ray.getPoint();		//ray point
        Vector v = ray.getVector();		//ray vector

        if(p0.equals(center))       	//if the starting point of the ray is the center
            return List.of(new GeoPoint(this,ray.getPoint(radius)));//return the intersection point

        Vector u=center.subtract(p0);	//the vector between center and ray
        double tm=v.dotProduct(u); 		//the scale for the ray in order to get parallel to the sphere center
        double d=Math.sqrt(u.lengthSquared()-tm*tm);//get the distance between the ray and the sphere center
        //check if d is bigger then radius-the ray doesn't cross the sphere
        if (d>radius)
            return null;
        double th=Math.sqrt(radius*radius-d*d);//according Pythagoras
        double t1=tm+th;
        double t2=tm-th;
        if(t1>0&&t2>0&&!isZero(ray.getPoint(t1).subtract(center).dotProduct(v))&&!isZero(ray.getPoint(t2).subtract(center).dotProduct(v))) //if orthogonal -> no intersection
            return List.of(new GeoPoint(this,ray.getPoint(t1)),new GeoPoint(this,ray.getPoint(t2)));
        else if(t1>0&&!isZero(ray.getPoint(t1).subtract(center).dotProduct(v))) //if only t1 is not orthogonal and positive
            return List.of(new GeoPoint(this,ray.getPoint(t1)));
        else if(t2>0&&!isZero(ray.getPoint(t2).subtract(center).dotProduct(v))) //if only t2 is not orthogonal and positive
            return List.of(new GeoPoint(this,ray.getPoint(t2)));
        else
            return null;//no intersections
    }

}