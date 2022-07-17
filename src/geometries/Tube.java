package geometries;

import primitives.Util;
import primitives.Vector;
import primitives.Point;
import primitives.Ray;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Tube extends Geometry {
    protected Ray axisRay;
    protected double radius;
/************** ctor *****************/
    /**
     * ctor that gets 2 parameters
     * @param axisRay
     * @param radius
     */
    public Tube(Ray axisRay, double radius) {
        super();
        if(Util.isZero(radius) || radius < 0)
            throw new IllegalArgumentException("Zero or negative radius");
        this.axisRay = axisRay;
        this.radius = radius;
    }
    /*************** gets *****************/
    /**
     * @return the axisray
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }
    /**
     * @param p
     * @return the normal
     */

    /*************** admin *****************/
    @Override
    public Vector getNormal(Point p) {
        Vector v = p.subtract(axisRay.getPoint());
        double t = alignZero(axisRay.getVector().dotProduct(v));
        Point o = axisRay.getPoint().add(axisRay.getVector().scale(t));
        Vector N = p.subtract(o);
        return N.normalize();
    }

    @Override
    public String toString() {
        return "Tube [axisRay=" + axisRay + ", radius=" + radius + "]";
    }
    @Override
    public List<GeoPoint>  findGeoIntersectionsParticular(Ray ray) {
        return  null;

    }
    @Override
    protected void findMinMaxParticular() {
        // TODO Auto-generated method stub

    }
    public Point getKPointPosition() {
        return null;
    }
}
