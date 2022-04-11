package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracerBase {


    public RayTracerBasic(Scene _scene) {
        super(_scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        Point pointAndGeo = findClosestIntersection(ray);
        if (pointAndGeo != null)                    //if there is an intersection point- calc it's color
            return calcColor(pointAndGeo, ray);
        else                            //if the ray doesn't intersect anything- return the background color
            return scene.backGround;
    }

    /**
     * outer calcColor that adds ambient light, and call the inner calcColor
     * sending level of recursion, and initial mekadem k=1.
     *
     * @param point
     * @param ray
     * @return
     */
    private Color calcColor(Point point, Ray ray) {
        return this.scene.backGround;
    }

    /**
     * we need this func here ib order to have local findClosestIntersection
     * without calling the scene's geometries'es findGeoIntersections.... etc.
     *
     * @param ray
     * @return the closest intersection or null if there aren't.
     */
    private Point findClosestIntersection(Ray ray) {
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
    }
}

