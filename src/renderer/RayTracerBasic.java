package renderer;



import scene.Scene;
import primitives.*;
import geometries.Intersectable.*;
import lighting.*;
import primitives.Util.*;



import java.util.List;

public class RayTracerBasic extends RayTracerBase {


    public RayTracerBasic(Scene _scene) {
        super(_scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersectionsPoints = scene.geometries.findGeoIntersections(ray);
        if (intersectionsPoints == null)
            return scene.backGround;
        else
            return calcColor(ray.findClosestGeoPoint(intersectionsPoints), ray);
    }
    /**
     * Calculate the color of a certain point
     *
     * @param point
     * @return The color of the point (calculated with local effects)
     */
    public Color calcColor(GeoPoint point, Ray ray) {
        return scene.ambientLight.getIntensity().add(point.geometry.getEmission()).add(calcLocalEffects(point, ray));
    }

    /**
     * Calculate the effects of lights
     *
     * @param intersection
     * @param ray
     * @return The color resulted by local effecrs calculation
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getVector();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = primitives.Util.alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;
        int nShininess = intersection.geometry.GetMaterial().nShininess;

        Double3 kd = intersection.geometry.GetMaterial().KD;
        Double3 ks = intersection.geometry.GetMaterial().KS;
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = primitives.Util.alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // checks if nl == nv
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }


    /**
     * Calculates diffusive light
     * @param kd
     * @param l
     * @param n
     * @param lightIntensity
     * @return The color of diffusive effects
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        double ln = primitives.Util.alignZero(l.dotProduct(n));
        if (ln < 0)
            ln = ln * -1;
        return lightIntensity.scale(kd.scale(ln));
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
        double vr = primitives.Util.alignZero(v.scale(-1).dotProduct(r));
        if (vr < 0)
            vr = 0;
        vr = Math.pow(vr, nShininess);
        return lightIntensity.scale(ks.scale(vr));
    }


}

