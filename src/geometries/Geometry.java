package geometries;

import primitives.Color;
import primitives.Vector;

import primitives.Point;


import primitives.Point;
import primitives.Vector;

public interface Geometry extends Intersectable {
    /**
     * return the normal vector from the shape
     * @param p point to get the normal from {@link Point}
     * @return normal vector {@link Vector}
     */
    public Vector getNormal(Point p);
}

/*public abstract class Geometry extends Intersectable {
    public abstract primitives.Vector getNormal(Point p);

    protected Color emission =  Color.BLACK;;


    /**
     * @return the emission
     */
    /*public Color getEmission() {
        return emission;
    }

    /**
     *
     * @param emmission
     * @return This (of geometry)
     */
    /*public Geometry setEmission(Color emmission) {
        this.emission = emmission;
        return this;
    }*/
//}
//
