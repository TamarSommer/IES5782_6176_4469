package geometries;

import primitives.Color;
import primitives.Vector;

import primitives.Point;

public abstract class Geometry implements Intersectable {
    public abstract primitives.Vector getNormal(Point p);

    protected Color emission =  Color.BLACK;;


    /**
     * @return the emission
     */
    public Color getEmission() {
        return emission;
    }

    /**
     *
     * @param emmission
     * @return This (of geometry)
     */
    public Geometry setEmission(Color emmission) {
        this.emission = emmission;
        return this;
    }
}
//
