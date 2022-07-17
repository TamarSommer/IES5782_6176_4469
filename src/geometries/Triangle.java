package geometries;

import primitives.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;

public class Triangle extends Polygon {
    public Triangle (Point p1,Point p2, Point p3) {
        super(p1,p2,p3);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Vector getNormal(Point p){
        return super.getNormal(p);
    }


    public List<GeoPoint> findGeoIntersectionsParticular(Ray ray) {
        List<GeoPoint> rayPoints = this.plane.findGeoIntersectionsParticular(ray);
        if (rayPoints == null) {
            return null;
        } else {
            GeoPoint P;
            for(Iterator var4 = rayPoints.iterator(); var4.hasNext(); P.geometry = this) {
                P = (GeoPoint)var4.next();
            }

            Vector v1 = ((Point)this.vertices.get(0)).subtract(ray.getPoint());
            Vector v2 = ((Point)this.vertices.get(1)).subtract(ray.getPoint());
            Vector v3 = ((Point)this.vertices.get(2)).subtract(ray.getPoint());
            Vector n1 = v1.crossProduct(v2).normalize();
            Vector n2 = v2.crossProduct(v3).normalize();
            Vector n3 = v3.crossProduct(v1).normalize();
            if (Util.alignZero(n1.dotProduct(ray.getVector())) > 0.0D && Util.alignZero(n2.dotProduct(ray.getVector())) > 0.0D && Util.alignZero(n3.dotProduct(ray.getVector())) > 0.0D) {
                return rayPoints;
            } else if (Util.alignZero(n1.dotProduct(ray.getVector())) < 0.0D && Util.alignZero(n2.dotProduct(ray.getVector())) < 0.0D && Util.alignZero(n3.dotProduct(ray.getVector())) < 0.0D) {
                return rayPoints;
            } else {
                return !Util.isZero(n1.dotProduct(ray.getVector())) && !Util.isZero(n2.dotProduct(ray.getVector())) && !Util.isZero(n3.dotProduct(ray.getVector())) ? null : null;
            }
        }
    }
    /*@Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray)
    {
        List<GeoPoint> resultPoint = plane.findGeoIntersectionsHelper(ray);

        if (resultPoint == null) // In case there is no intersection with the plane return null
            return null;

        Vector v1 = vertices.get(0).subtract(ray.getPoint());
        Vector v2 = vertices.get(1).subtract(ray.getPoint());
        Vector v3 = vertices.get(2).subtract(ray.getPoint());
        Vector n1 = (v1.crossProduct(v2)).normalize();
        Vector n2 = (v2.crossProduct(v3)).normalize();
        Vector n3 = (v3.crossProduct(v1)).normalize();
        double t1 = alignZero(n1.dotProduct(ray.getVector()));
        double t2 = alignZero(n2.dotProduct(ray.getVector()));
        double t3 = alignZero(n3.dotProduct(ray.getVector()));

        if (t1 == 0  || t2 == 0 || t3 == 0) // In case one or more of the scalars equals zero
            return null; // that mean the point is not inside the triangle

        if ((t1 > 0 && t2 > 0 && t3 > 0) || (t1 < 0 && t2 < 0 && t3 < 0)) { // In case the all scalars are in the same sign, the point is in the triangle
            LinkedList<GeoPoint> result = new LinkedList<GeoPoint>();
            result.add(new GeoPoint(this, resultPoint.get(0).point)); //to return this of Triangle
            return result;
        } else
            return null;  //If the scalars are in a different sign

    }*/
}