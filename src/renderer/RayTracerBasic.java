package renderer;
import java.util.List;

import lighting.LightSource;
import primitives.*;
import scene.Scene;

import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;

public class RayTracerBasic extends RayTracerBase {
    /**
     * @param sc
     * Ctor using super class constructor
     */
    public RayTracerBasic(Scene sc) {
        super(sc);
    }
    private static final double DELTA = 0.1;

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * Function that calculates the color for the nearest intersection point,
     * if no intersection points are returned the color of the background
     * @param ray Ray value
     * @return Color
     **/
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    private Color calcColor(GeoPoint geoPoint, Ray ray)
    {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, new Double3(DELTA)).add(scene.ambientLight.getIntensity());
    }

    /**
     * Function for calculating a point color
     *
     * @return Color
     * */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        //Color color = scene.ambientLight.getIntensity();
        Color color =intersection.geometry.getEmission();
        color=color.add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k, intersection.geometry.getNormal(intersection.point)));
    }

    /**
     * calc the global effects- relection and refraction.
     * this func call "calcColor" in recursion to add more and more global effects.
     * @param intersection
     * @param ray
     * @param level of recursion- goes down each time till it gets to 1
     * @param k- mekadem of reflection and refraction so far
     * @return
     */
    private Color calcGlobalEffects(GeoPoint geopoint, Ray inRay, int level, double k)
    {
        Vector n = geopoint.geometry.getNormal(geopoint.point);//normal to geometry in point

        Color color = Color.BLACK;
        Material material = geopoint.geometry.getMaterial();

        //improve: check the conditions in the beginning
        if (level == 1 || k < MIN_CALC_COLOR_K) //stop recursion when it gets to the min limit
        {
            return color;
        }

        Double3 kr = material.KR;
        Double3 kkr = kr.scale(k);
        if (kkr.lowerThan(MIN_CALC_COLOR_K)) //stop recursion when it gets to the min limit
        {
            Ray reflectedRay = constructReflectedRay(geopoint.point, inRay, n);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
        }
        Double3 kt = material.KT;
        Double3 kkt = kt.scale(k);
        if (kkt.lowerThan(MIN_CALC_COLOR_K)) //stop recursion when it gets to the min limit
        {
            Ray refractedRay = constructRefractedRay(geopoint.point, inRay, n);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
        }
        return color;
    }


    private Color calcLocalEffects(GeoPoint intersection, Ray ray, Double3 k) {
        Vector v = ray.getVector().normalize();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = Util.alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.nShininess;
        Double3 kd = material.KD;
        Double3 ks = material.KS;
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = Util.alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Double3 ktr = transparency(lightSource, l, n, intersection);
                //if (unshaded(l, n, intersection, lightSource)) {
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(lightIntensity.scale(((calcDiffusive(kd, nl)).add(calcSpecular(ks, l, n, v, nShininess)))));
                }
                //	}
            }
        }
        return color;
    }

    /**
     * help function that calculate the difusive effect
     *
     * @author Tamar Gavrieli & Odeya Sadoun
     * @param kd double value
     * @param nl double value

     * @return double value for calcDiffusive
     * */
    private double calcDiffusive(double kd, double nl)
    {
        return alignZero(Math.abs(nl)*kd);
    }

    /**
     * Calculate specular light
     * @param ks
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return The color of specular reflection
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));
        double vr = alignZero(v.scale(-1).dotProduct(r));
        if (vr < 0)
            vr = 0;
        vr = Math.pow(vr, nShininess);
        return lightIntensity.scale(ks.scale(vr));
    }

    /**
     * search for shadow shape
     * @param l from light source to point
     * @param n the normal
     * @param geopoint the point
     * @return if the point is unshaded. it means- if there is no shade on it- it should have light.
     */
    private static final double EPS = 0.1;

    private boolean unshaded(Vector l, Vector n, GeoPoint geopoint, LightSource ls) {
        double maxDistance= ls.getDistance(geopoint.point);
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(n.dotProduct(lightDirection) > 0 ? EPS : -EPS);
        Point point = geopoint.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return true;
        //
        for (GeoPoint gp : intersections) {
            double distance = gp.point.distance(geopoint.point);
            if(distance >= maxDistance)
                intersections.remove(gp);
        }
        return intersections.isEmpty();
    }

    /**
     * this func returns a new ray- the refracted ray comes from the point because of the light- inRay
     * @param pointGeo
     * @param inRay
     * @param n
     * @return RefractedRay
     */
    private Ray constructRefractedRay(Point pointGeo, Ray inRay, Vector n) {
        return new Ray(pointGeo, inRay.getVector(), n);
    }

    /**
     * this func returns a new ray- the reflected ray comes from the point because of the light- inRay
     * @param pointGeo
     * @param inRay
     * @param n
     * @return ReflectedRay
     */
    private Ray constructReflectedRay(Point pointGeo, Ray inRay, Vector n)
    {
        Vector v = inRay.getVector();
        double vn = v.dotProduct(n);

        if (Util.isZero(vn))
        {
            return null;
        }
        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(pointGeo, r, n);
    }

    /**
     * Gets the discount-factor of the half or full shading on the intersection-geoPoint regarding one of the light-sources.
     * @param light the light-source
     * @param l the light-vector of the light-source
     * @param n the intersected-geometry's normal
     * @param geoPoint the intersection-geoPoint
     * @return The discount-factor of the shading on this intersection-geoPoint
     */
    private Double3 transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection,n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return new Double3(1);
        Double3 ktr = new Double3(1);
        double lightDistance = light.getDistance(geoPoint.point);
        for (GeoPoint gp : intersections) {
            if(Util.alignZero(gp.point.distance(geoPoint.point)-lightDistance)<=0) {
                ktr= ktr.product(gp.geometry.getMaterial().KT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K))
                    return Double3.ZERO;
            }
        }
        return ktr;
    }


    /**
     * Finds the closest intersection-geoPoint to the starting-point of the given ray.
     * @param ray the given constructed-ray
     * @return The closest intersection-geoPoint to the ray's starting-point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(ray);
        return intersections == null ? null : ray.findClosestGeoPoint(intersections);
    }
}