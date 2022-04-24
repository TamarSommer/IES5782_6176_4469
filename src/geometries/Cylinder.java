package geometries;

import primitives.Ray;
import primitives.Vector;

import primitives.Point;

import java.util.List;

public class Cylinder extends Tube {
    double height;
    /*************** ctor *****************/
    /**
     * ctor that gets 3 parameters
     * @param radius
     * @param ray
     * @param height
     */
    public Cylinder(double radius, Ray ray, double height) {
        super(ray, radius);
        if (height<0)
            throw new IllegalArgumentException("height must be bigger than zero");
        this.height = height;
    }
    /*************** calculating functions *****************/

    /*************** get *****************/
    /**
     * @return the height of the cylinder
     */
    public double getHeight() {return height;}
    /*************** admin *****************/
    /**
     * @param p
     * @return the noraml
     */
    @Override
    public Vector getNormal(Point p) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toString() {return "Cylinder [height=" + height + "]";
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }

}