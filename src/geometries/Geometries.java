package geometries;


import primitives.Point;
import primitives.Ray;

import java.util.*;

public class Geometries extends Borderable{


    private List<Borderable> geometriesInScene;

    public Geometries() {
        this.geometriesInScene = new ArrayList();
    }

    public Geometries(Borderable... geometries) {
        this.geometriesInScene = new ArrayList(Arrays.asList(geometries));
    }

    public void add(Borderable... geometries) {
        if (geometries != null) {
            this.geometriesInScene.addAll(Arrays.asList(geometries));
        }

    }

    public List<GeoPoint> findGeoIntersectionsParticular(Ray ray) {
        List<GeoPoint> temp = new ArrayList();
        Iterator var4 = this.geometriesInScene.iterator();

        while(var4.hasNext()) {
            Intersectable intersectable = (Intersectable)var4.next();
            List<GeoPoint> intersection = intersectable.findGeoIntersections(ray);
            if (intersection != null) {
                temp.addAll(intersection);
            }
        }

        if (temp.isEmpty()) {
            return null;
        } else {
            return temp;
        }
    }

    protected void findMinMaxParticular() {
        minX = Double.POSITIVE_INFINITY;
        maxX = Double.NEGATIVE_INFINITY;
        minY = Double.POSITIVE_INFINITY;
        maxY = Double.NEGATIVE_INFINITY;
        minZ = Double.POSITIVE_INFINITY;
        maxZ = Double.NEGATIVE_INFINITY;
        Iterator var2 = this.geometriesInScene.iterator();

        while(var2.hasNext()) {
            Borderable g = (Borderable)var2.next();
            g.findMinMax();
            if (g.minX < this.minX) {
                this.minX = g.minX;
            }

            if (g.minY < this.minY) {
                this.minY = g.minY;
            }

            if (g.minZ < this.minZ) {
                this.minZ = g.minZ;
            }

            if (g.maxX > this.maxX) {
                this.maxX = g.maxX;
            }

            if (g.maxY > this.maxY) {
                this.maxY = g.maxY;
            }

            if (g.maxZ > this.maxZ) {
                this.maxZ = g.maxZ;
            }
        }


    }



    /*public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        if (_intersectablesList.isEmpty()) // In case the collection is empty
            return null;

        List<GeoPoint> points = null, result;
        for (Intersectable body: _intersectablesList) {
            result = body.findGeoIntersectionsHelper(ray);
            if(result != null){
                if(points == null)
                    points = new LinkedList<GeoPoint>(result);
                else
                    points.addAll(result);
            }
        }
        return points;
    }*/
}