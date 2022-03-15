package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

public class Sphere implements Geometry {
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
        if(Util.isZero(radius) || radius < 0)
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

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}