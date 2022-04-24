package geometries;

import primitives.*;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Intersectable {
    public abstract List<Point> findIntersections(Ray ray){
        List<Intersectable.GeoPoint> geoList = this.findGeoIntersections(ray);
        return geoList == null ? null : (List)geoList.stream().map((gp) -> {
            return gp.point;
        }).collect(Collectors.toList()); }
    /**
     * The class GeoPoint contains Geometry and Point
     * @author Dvori and Tamar
     *
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * Constructor of GeoPoint
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }
        public String toString() {
            return "GP{G=" + this.geometry + ", P=" + this.point + '}';
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            } else if (obj == null) {
                return false;
            } else if (this.getClass() != obj.getClass()) {
                return false;
            } else {
                Intersectable.GeoPoint other = (Intersectable.GeoPoint)obj;
                if (this.geometry == null) {
                    if (other.geometry != null) {
                        return false;
                    }
                } else if (!this.geometry.equals(other.geometry)) {
                    return false;
                }

                if (this.point == null) {
                    if (other.point != null) {
                        return false;
                    }
                } else if (!this.point.equals(other.point)) {
                    return false;
                }

                return true;
            }
        }
        public abstract List<GeoPoint> findGeoIntersections(Ray ray){this.findGeoIntersectionsHelper(ray);}
        protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);
    }



}
