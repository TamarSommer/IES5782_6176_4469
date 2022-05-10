package renderer;

import java.util.List;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

/**
 * RayTracerBasic's class. Extends the abstract-class RayTracerBase.
 * Represents the using of the rays that was traced from the camera, integrating with the scene - when each pixel traces a single ray.
 */
public class RayTracerBasic extends RayTracerBase {

    public static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * RayTracerBasic's Constructor
     * @param scene The scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
        // TODO Auto-generated constructor stub
    }



    /**
     * Function that calculates the color for the nearest intersection point,
     * if no intersection points are returned the color of the background
     * @param ray Ray value
     * @return Color
     **/
    @Override
    public Color traceRay(Ray ray) {
        //	List<GeoPoint> intersections = scene.geometries.findGeoIntersectionsHelper(ray);
        //if(intersections == null)
        //return  scene.background;
        //GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }


    /**
     * Finds the closest intersection-geoPoint to the starting-point of the given ray.
     * @param ray the given constructed-ray
     * @return The closest intersection-geoPoint to the ray's starting-point

    private GeoPoint findClosestIntersection(Ray ray) {
    List<GeoPoint> intersections = this.scene.geometries.findGeoIntersectionsHelper(ray);
    return intersections == null ? null : ray.findClosestGeoPoint(intersections);
    }

     */

    /**
     * Function for calculating a point color
     * @param point Point value
     * @return Color
     * */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        //Color color = scene.ambientLight.getIntensity();
        Color color =intersection.geometry.getEmission();
        color=color.add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k, intersection.geometry.getNormal(intersection.point)));
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
        Double3 ks = material.Ks;
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = Util.alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                //Double3 ktr = transparency(lightSource, l, n, intersection);
                //if (unshaded(l, n, intersection, lightSource)) {
               // if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point);
                    color = color.add(lightIntensity.scale(((calcDiffusive(kd, nl)).add(calcSpecular(ks, l, n, v, nShininess)))));

            }
        }
        return color;
    }

    /**
     * Calculates the difusion-addition to the geoPoint's color, determined by one of the light-sources.
     * @param kd the defusion-factor of the geometry's material
     * @param nl the dot-product of the intersected-geometry's normal and the light-vector of the light-source
     * @return The difusion-addition to the geoPoint's color
     */
    private Double3 calcDiffusive(Double3 kd, double nl) {
        //double nl = alignZero(n.dotProduct(l));
        if(nl < 0)
            nl = (-1)*nl;
        return kd.scale(nl);
    }

    /**
     * Calculates the specularization-addition to the geoPoint's color, determined by one of the light-sources.
     * @param ks the specularization-factor of the geometry's material
     * @param l the light-vector of the light-source
     * @param n the intersected-geometry's normal
     * @param nl the dot-product of the intersected-geometry's normal and the light-vector of the light-source (SAVES RECALCULATING)
     * @param v the direction-vector of the constructed-ray
     * @param nShininess the specularization's-exponential-factor of the geometry's material
     * @return The specularization-addition to the geoPoint's color, determined by one of the light-sources
     */
    private Double3 calcSpecular(Double3 ks, Vector l, Vector n, Vector v, int nShininess) {
        //double twiceNL = alignZero(n.dotProduct(l)*2);
        //Vector r = l.subtract(n.scale(twiceNL)).normalized();
        double nl = Util.alignZero(n.dotProduct(l));
        Vector r = l.subtract(n.scale(2*nl)).normalize();
        double vr = Util.alignZero(v.dotProduct(r));
        if(vr>0)
            return new Double3(0,0,0);

        double vrPowed = Math.pow((-1)*vr,nShininess);
        return ks.scale(vrPowed);
    }


    private Color calcColor(GeoPoint geoPoint, Ray ray)
    {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, new Double3(DELTA)).add(scene.ambientLight.getIntensity());
    }

    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k, Vector n) {
        if (level == 1 || k.lowerThan(MIN_CALC_COLOR_K))
        {
            return Color.BLACK;
        }
        Vector v = ray.getVector();
        Color color = Color.BLACK;
        Double3 kr = gp.geometry.getMaterial().KR;
        Double3 kkr = k.product(kr);


        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            Ray reflectedRay = constructReflectedRay( gp.point, ray, n);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null)
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
            else
                color = color.add(scene.background.scale(kr));
        }


        Double3 kt = gp.geometry.getMaterial().KT;
        Double3 kkt = k.product(kt);

        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            Ray refractedRay = constructRefractedRay(gp.point, v, n);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);

            if (refractedPoint != null)
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
            else
                color = color.add(scene.background.scale(kr));
        }
        return color;
    }

    /**
     * Constructs a reflected ray to a former-ray which ends on the given point and it's direction is the given direction
     * @param point the former-ray's end point - and the reflected-ray's starting point
     * @param v the former-ray's direction
     * @param n the normal to the geometry on which the point lays ( - the end of the former-ray)
     * @return the constructed reflected-ray
     */
    private Ray constructReflectedRay(Point point, Ray  ray, Vector n) {
        Vector v = ray.getVector();
        double vn = Util.alignZero(v.dotProduct(n));
        // if v.dotProduct(n)==0 then the ray we construct is tangent to the geometry:
        if (Util.isZero(vn))
            return null;
        Vector dir = v.subtract(n.scale(2*vn));
        return new Ray(point, dir,n);
    }


    /**
     * Constructs a refracted-ray to a former-ray which ends on the given point and it's direction is the given direction
     * @param point the former-ray's end point - and the refracted-ray's starting point
     * @param v the former-ray's direction - and the refracted-ray's direction
     * @param n the normal to the geometry on which the point lays ( - the end of the former-ray)
     * @return the constructed refracted-ray
     */
    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, v, n);
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