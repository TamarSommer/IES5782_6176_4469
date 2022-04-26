package geometries;

import primitives.*;

import java.util.List;

public class Triangle extends Polygon {
    public Triangle (Point p1,Point p2, Point p3) {
        super(p1,p2,p3);
    }

//    @Override
//    public Vector getNormal(Point point) {
//        return super.getNormal(point);
//    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public List<Point> findIntersections(Ray ray)
    {
        List<Point> rayPoints = plane.findIntersections(ray);
        if (rayPoints == null)
            return null;
        //for (Point P : rayPoints)
        //{
        //P. = this;
        //}
        //check if the point in out or on the triangle:
        Vector v1 = vertices.get(0).subtract(ray.getPoint());
        Vector v2 = vertices.get(1).subtract(ray.getPoint());
        Vector v3 = vertices.get(2).subtract(ray.getPoint());

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();


        //The point is inside if all have the same sign (+/-)

        if (Util.alignZero(n1.dotProduct(ray.getVector())) > 0 && Util.alignZero(n2.dotProduct(ray.getVector())) > 0 && Util.alignZero(n3.dotProduct(ray.getVector())) > 0)
        {
            return rayPoints;
        }
        else if (Util.alignZero(n1.dotProduct(ray.getVector())) < 0 && Util.alignZero(n2.dotProduct(ray.getVector())) < 0 && Util.alignZero(n3.dotProduct(ray.getVector())) < 0)
        {
            return rayPoints;
        }
        if (Util.isZero(n1.dotProduct(ray.getVector())) || Util.isZero(n2.dotProduct(ray.getVector())) || Util.isZero(n3.dotProduct(ray.getVector())))
            return null; //there is no instruction point
        return null;
    }
}