package geometries;

import primitives.Ray;
import primitives.Vector;

import primitives.Point;

public class Cylinder extends Tube implements Geometry{
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
    public String toString() {return "Cylinder [height=" + height + "]";}


}