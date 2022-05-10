package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import geometries.Intersectable;


import java.util.List;

public class RayTracerBasic extends RayTracerBase {


    public RayTracerBasic(Scene _scene) {
        super(_scene);
    }

    /**
     * @param ray
     * @return the color of the pixel that the ray pass through it
     */
    public Color traceRay(Ray ray)
    {
        GeoPoint pointAndGeo=findClosestIntersection(ray);
        if(pointAndGeo!=null)					//if there is an intersection point- calc it's color
            return calcColor(pointAndGeo);
        else							//if the ray doesn't intersect anything- return the background color
            return scene.backGround;
    }

    /**
     * outer calcColor that adds ambient light, and call the inner calcColor
     * sending level of recursion, and initial mekadem k=1.
     *
     * @param gPoint
     * @return
     */



    private Color calcColor(GeoPoint gPoint) {
        return (gPoint.geometry.getEmission()).add(scene.ambientLight.getIntensity());
        //return scene.ambientLight.getIntensity().add(gPoint.geometry.getEmission());
    }

    /**
     * we need this func here ib order to have local findClosestIntersection
     * without calling the scene's geometries'es findGeoIntersections.... etc.
     *
     * @param ray
     * @return the closest intersection or null if there aren't.
     */
  /*  private Point findClosestIntersection(Ray ray) {
        if (ray == null) {
            return null;
        }

        Point closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        Point ray_p0 = ray.getPoint();

        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null)
            return null;//no intersection

        for (Point point : intersections) //select the closest distance
        {
            double distance = ray_p0.distance(point);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestPoint = point;
            }
        }
        return closestPoint;
    }*/
    /**
     * we need this func here ib order to have local findClosestIntersection
     * without calling the scene's geometries'es findGeoIntersections.... etc.
     * @param ray
     * @return the closest intersection or null if there aren't.
     */
    private GeoPoint findClosestIntersection(Ray ray)
    {
        if (ray == null) {
            return null;
        }

        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        Point ray_p0 = ray.getPoint();

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return null;//no intersection

        for (GeoPoint geoPoint : intersections) //select the closest distance
        {
            double distance = ray_p0.distance(geoPoint.point);
            if (distance < closestDistance)
            {
                closestDistance = distance;
                closestPoint = geoPoint;
            }
        }
        return closestPoint;
    }
}

